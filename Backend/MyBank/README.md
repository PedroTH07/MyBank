# MyBank backend ☕

O backend do MyBank é feito usando Spring Boot, Spring Security e um banco de dados PostgreSQL acessado via container docker.

O fluxo de autenticação é feito com tokens jwt (pela lib java-jwt do auth0) em cookies HttpOnly (não podem ser lidos via JavaScript). Ao estar autenticado, um usuário pode fazer transações monetárias para outro usuário.