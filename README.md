# MyBank

Este é um simples projeto de um **banco simplificado**, com autenticação e transações monetárias entre usuários (nenhum dinheiro real envolvido).

🐳 O projeto usa containers dockers do backend feito com Java Spring, do frontend feito em Angular, e do banco de dados relacional PostgreSQL. Para orquestrar e subir os containers juntos é usado o docker compose para fazer o build do back e do frontend e subir a imagem do postgres.

**Para mais detalhes do backend [clique aqui](./Backend/MyBank/README.md)**

**Para mais detalhes do frontend [clique aqui](./Frontend/MyBank/README.md)**

## 🚀 Como rodar o projeto localmente

Para rodar o projeto localmente será necessário ter o docker e o docker compose instalados (ou apenas o docker desktop)

1. Clone o projeto na sua máquina
```bash
git clone https://github.com/PedroTH07/MyBank.git
cd MyBank
```

2. Suba os containers com o Docker compose
```bash
docker compose up -d
```

acesse o frontend na URL `localhost:4200`