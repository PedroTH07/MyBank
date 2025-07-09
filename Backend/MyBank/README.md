# MyBank backend ☕

O backend do MyBank é feito usando Spring Boot, Spring Security e um banco de dados PostgreSQL acessado via container docker.

O fluxo de autenticação é feito com tokens jwt (pela lib java-jwt do auth0) em cookies HttpOnly (não podem ser lidos via JavaScript). Ao estar autenticado, um usuário pode fazer transações monetárias para outro usuário.

## 🛣️ Rotas

### `/auth`

```http
POST /register
```
> Registra um novo usuário e retorna um cookie http only com um token JWT dentro e um cookie para o refresh

**🔓 Autenticação: não requerida**

<br/>
```http
POST /login
```
> Faz a autenticação de um usuário e retorna um cookie http only com um token JWT dentro e um cookie para o refresh

**🔓 Autenticação: não requerida**

<br/>
```http
POST /refresh
```
> Rota para atualizar o cookie sem a necessidade de refazer o login

**🔐 Autenticação: necessário o envio do cookie refresh token**
