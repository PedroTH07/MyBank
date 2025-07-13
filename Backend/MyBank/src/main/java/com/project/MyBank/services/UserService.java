package com.project.MyBank.services;

import com.project.MyBank.domain.dtos.PaymentRequestDto;
import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserRepository;
import com.project.MyBank.domain.dtos.PaymentResponseDto;
import com.project.MyBank.domain.dtos.UserPaymentResponseDto;
import com.project.MyBank.domain.dtos.UserResponseDto;
import com.project.MyBank.domain.exceptions.CookieNotFoundException;
import com.project.MyBank.domain.exceptions.IncorrectPasswordException;
import com.project.MyBank.domain.exceptions.InsufficientMoneyException;
import com.project.MyBank.infra.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> findAll() { return this.repository.findAll(); }

    public UserResponseDto thisUser(HttpServletRequest request) throws CookieNotFoundException, UsernameNotFoundException {
        var token = this.jwtService.recoverToken(request);
        if (token == null) throw new CookieNotFoundException("cookie jwt não encontrado em /users/me");

        String subject = this.jwtService.validateToken(token);

        User user = this.repository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("user not found at /users/me"));
        return new UserResponseDto(user);
    }

    @Transactional
    public PaymentResponseDto pay(PaymentRequestDto data) {
        List<User> users = this.repository.findUsersForPayment(data.payerEmail(), data.payeeEmail());
        if (users.size() != 2) throw new UsernameNotFoundException("um ou mais usuários não enconrados");

        User payerUser, payeeUser;

        if (users.getFirst().getEmail().equals(data.payerEmail())) {
            payerUser = users.getFirst();
            payeeUser = users.getLast();
        } else {
            payerUser = users.getLast();
            payeeUser = users.getFirst();
        }

        if (!encoder.matches(data.payerPassword(), payerUser.getPassword())) {
            throw new IncorrectPasswordException("senha incorreta");
        }

        if (payerUser.getMoney() < data.amount()) throw new InsufficientMoneyException("dinheiro insuficiente para a transação");

        payerUser.setMoney(payerUser.getMoney() - data.amount());
        payeeUser.setMoney(payeeUser.getMoney() + data.amount());

        List<User> updatedUsers = this.repository.saveAll(List.of(payerUser, payeeUser));
        List<UserPaymentResponseDto> responseUsers = updatedUsers
                .stream()
                .map(UserPaymentResponseDto::new)
                .toList();

        return new PaymentResponseDto(data.amount(), responseUsers);
    }

}
