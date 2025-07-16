# 📦 DevOps App — Deploy com Helm + Minikube

## 📝 Descrição

Aplicação desenvolvida em Java Spring Boot com um serviço de envio de e-mails e um banco de dados MySQL, implantada em um cluster local Kubernetes (Minikube) utilizando Helm Charts.

## ⚙️ Tecnologias Utilizadas

- Java Spring Boot

- MySQL

- Docker

- Kubernetes (Minikube)

- Helm

- Ingress Controller

- Bash Script (para automação)

- Secrets (para variáveis sensíveis)

## 🚀 Como executar localmente
### 1. Pré-requisitos
 
 - Docker instalado

 - Minikube instalado

 - Helm instalado

 - Git Bash ou terminal compatível com Bash (no Windows)

### 2. Clonar o repositório

git clone [https://github.com/seu-usuario/devops-app.git](https://github.com/natycristina/Trabalho2-devops.git)

cd devops-app

### 3. Executar o script de build

chmod +x scripts/build.sh

./scripts/build.sh

*-* Esse script: *-*

- Faz build das imagens (spring-app, email-service, mysql)

- Carrega no Minikube

- Inicia o cluster

- Instala o Helm Chart

### 4. Acessar a aplicação
Antes, adicione no seu arquivo hosts:

127.0.0.1 k8s.local

🌐 Acesse: http://k8s.local

📧 Para testar o envio de e-mails, use o endpoint /email
