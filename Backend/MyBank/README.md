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

<br/>

### `/users`

```http
GET /
```
> Retorna todos os usuários registrados (rota de teste)

**🔓 Autenticação: não requerida**

<br/>

```http
GET /me
```
> Retorna as informações daquele usuário, pego pelo token JWT dentro do cookie

**🔐 Autenticação: necessário**

<br/>

```http
POST /pay
```
> Rota para realizar transações entre usários

**🔐 Autenticação: necessário**

<br />

```http
PUT /image
```
> Atualiza a imagem (ou adiciona uma caso não tenha) de um usuário

**🔐 Autenticação: necessária**