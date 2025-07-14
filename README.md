# 🧠 ForumHub

Projeto de fórum de perguntas e respostas desenvolvido com Java e Spring Boot, inspirado na estrutura de fóruns como o da Alura. O objetivo é praticar os principais conceitos de APIs REST, autenticação com JWT, persistência com JPA, e versionamento de banco de dados com Flyway.

---

## 🚀 Tecnologias utilizadas

- Java 21  
- Spring Boot 3.5  
- Spring Web  
- Spring Security  
- Spring Data JPA  
- MySQL  
- Flyway (controle de versões do banco)  
- Swagger UI (documentação da API)  
- Maven  
- JWT (JSON Web Token)

---

## ⚙️ Funcionalidades implementadas

- Cadastro e autenticação de usuários
- Criação de tópicos
- Respostas em tópicos
- Proteção de rotas com JWT
- Validações com Bean Validation (`@Valid`)
- Documentação de endpoints com Swagger
- Migrations com Flyway

---

## 🛠️ Como executar localmente

### ✅ Pré-requisitos:

- Java 21
- MySQL rodando localmente (porta 3306)
- Maven

### 📥 Passos:

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/forumhub.git
cd forumhub

# 2. Configure o banco de dados
# Edite src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=root
spring.datasource.password=sua_senha

# 3. Rode a aplicação
./mvnw spring-boot:run



📖 Documentação com Swagger
Após rodar o projeto, acesse:

bash
http://localhost:8080/swagger-ui.html
Por lá, é possível testar os endpoints diretamente pela interface.

🔐 Segurança com JWT
Endpoint de autenticação: POST /auth/login

Envie login e senha válidos

Receba um token JWT e use no cabeçalho das próximas requisições:

http
Authorization: Bearer seu_token_aqui

🧾 Migrations com Flyway
A estrutura do banco de dados é controlada pelo Flyway. Os arquivos .sql estão na pasta:
src/main/resources/db/migration/

Se uma migration falhar, você pode executar:
bash
./mvnw flyway:repair


🗂️ Estrutura do projeto
forumhub
├── controller         # Camada REST
├── dto                # Objetos de transferência de dados
├── entity             # Entidades JPA
├── repository         # Interfaces de acesso ao banco
├── service            # Regras de negócio
└── infra              # Segurança, exceções e configurações
📌 Status do projeto
🟡 Em desenvolvimento — novas funcionalidades serão adicionadas em breve.

👨‍💻 Autor
Desenvolvido por Yago Carvalho
📸 Instagram: @iyagocarvalhodev
🔗 GitHub: github.com/YagoACarvalho

⭐ Contribua
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests para sugerir melhorias ou correções.

