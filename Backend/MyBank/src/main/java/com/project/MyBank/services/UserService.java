package com.project.MyBank.services;

import com.project.MyBank.domain.dtos.*;
import com.project.MyBank.domain.Transaction;
import com.project.MyBank.domain.TransactionRepository;
import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserRepository;
import com.project.MyBank.domain.exceptions.CookieNotFoundException;
import com.project.MyBank.domain.exceptions.IncorrectPasswordException;
import com.project.MyBank.domain.exceptions.InsufficientMoneyException;
import com.project.MyBank.infra.security.JwtService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private TransactionRepository transactionRepository;

    private static final String UPLOAD_DIR = "uploads/";


    public List<User> findAll() { return this.repository.findAll(); }

    public UserWithTransactionDto thisUser(HttpServletRequest request) throws CookieNotFoundException, UsernameNotFoundException {
        String subject = this.getUserEmailBySession(request);

        User user = this.repository.findByEmailWithTransactions(subject).orElseThrow(() -> new UsernameNotFoundException("user not found at /users/me"));
        return new UserWithTransactionDto(user);
    }

    @Transactional
    public String editImage(MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("nenhum arquivo enviado");

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = LocalDateTime.now() + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, file.getBytes());

        String userEmail = this.getUserEmailBySession(request);

        User user = this.repository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));

        user.setImageUrl(UPLOAD_DIR + fileName);
        this.repository.save(user);
        return UPLOAD_DIR + fileName;
    }

    public ResourceWithContentTypeDto getImage(String imageName) throws MalformedURLException {
        Path path = Paths.get(UPLOAD_DIR + imageName);
        Resource resource =  new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) throw new MalformedURLException("imagem não encontrada");
        
        String contentType = servletContext.getMimeType(path.toString());

        if (contentType == null) contentType = "application/octet-stream";

        return new ResourceWithContentTypeDto(resource, contentType);
    }

    @Transactional
    public PaymentResponseDto pay(PaymentRequestDto data, HttpServletRequest request) {
        String payerEmail = this.getUserEmailBySession(request);

        List<User> users = this.repository.findUsersForPayment(payerEmail, data.payeeEmail());
        if (users.size() != 2) throw new UsernameNotFoundException("um ou mais usuários não enconrados");

        User payerUser, payeeUser;

        if (users.getFirst().getEmail().equals(payerEmail)) {
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

        List<User> savedUsers = this.repository.saveAll(List.of(payerUser, payeeUser));
        List<UserPaymentResponseDto> responseUsers = savedUsers
                .stream()
                .map(UserPaymentResponseDto::new)
                .toList();
        
        this.transactionRepository.save(Transaction.newTransaction(payerUser, payeeUser, data.amount()));

        return new PaymentResponseDto(data.amount(), responseUsers);
    }


    private String getUserEmailBySession(HttpServletRequest request) {
        var token = this.jwtService.recoverToken(request);
        if (token == null) throw new CookieNotFoundException("cookie jwt não encontrado na requisição");

        return this.jwtService.validateToken(token);
    }
}
