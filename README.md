# Trabalho 1 - DevOps: Plataforma de Gestão de Vagas

Este repositório contém a implementação de uma aplicação web desenvolvida em **Java Spring Boot**. Ela permite a empresas cadastrar vagas e a profissionais se candidatarem, incluindo um serviço dedicado para **notificações por e-mail**. A aplicação é totalmente **containerizada com Docker** e orquestrada via **Docker Compose**, seguindo os requisitos da disciplina de DevOps.

Para uma descrição completa da arquitetura, funcionalidades, e detalhes técnicos, por favor, consulte o **[Arquivo PDF de Descrição da Aplicação](Descrição da aplicação - Nataly (1).pdf)**.

---

## 📦 Estrutura de Contêineres

A aplicação é composta por 3 contêineres principais:

* **`spring_app` (Backend):** O coração da aplicação, gerencia a lógica de negócios e as interações com o banco de dados e o serviço de e-mail.
  
* **`db` (MySQL):** Banco de dados relacional para persistência de todos os dados da aplicação.
  
* **`email-service` (Serviço de E-mail):** Microsserviço dedicado ao envio de e-mails transacionais (como notificações de entrevista).


**Observação sobre Comunicação:** Todos os contêineres se comunicam entre si utilizando seus respectivos nomes de serviço (ex: `spring_app` se conecta a `db` e `email-service`), sem a necessidade de expor portas para o `localhost` para comunicação interna. Apenas o `spring_app` expõe a porta 8080 para acesso externo do usuário.

---

## ⚙️ Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot, Spring Data JPA + Hibernate, Maven.
  
* **Banco de Dados:** MySQL 8.
  
* **Containerização:** Docker, Docker Compose.
  
* **Serviço de E-mail:** Spring Boot (Java 17), SMTP (Gmail).

---

## 🚀 Como Executar o Projeto com Docker

Para levantar e executar a aplicação, siga os passos abaixo. Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina.

1.  **Clone o Repositório:**
    
    git clone [https://github.com/natycristina/Trabalho1-devops.git](https://github.com/natycristina/Trabalho1-devops.git)
    
    cd Trabalho1-devops
  

3.  **Configurar Credenciais:**
    Edite o arquivo `docker-compose.yml` para configurar as credenciais do banco de dados e do serviço de e-mail.
    
    * No serviço `app`, altere `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD` para as credenciais do seu MySQL (ex: `root` e `root`).
      
    * No serviço `email-service`, forneça seu `SPRING_MAIL_USERNAME` (seu e-mail Gmail) e `SPRING_MAIL_PASSWORD` (sua senha de app do Gmail, se aplicável).

5.  **Execute os Contêineres:**
    No diretório raiz do projeto (onde está o `docker-compose.yml`), execute:
    
    docker-compose up --build -d
    
    * `--build`: Constrói as imagens dos seus serviços a partir dos Dockerfiles.
      
    * `-d`: Inicia os contêineres em modo "detached" (em segundo plano).

6.  **Acesse a Aplicação:**
   
    Acesse a interface web da aplicação em seu navegador:
    
    [http://localhost:8080](http://localhost:8080)

8.  **Parar os Contêineres:**
    Para derrubar e remover os contêineres (e seus volumes, para limpar os dados do banco de dados):
    
    docker-compose down -v

---
