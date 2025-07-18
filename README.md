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

### 🔧 3. Configuração Inicial Obrigatória

Antes de executar o script de instalação ou instalar com o Helm, você deve editar os seguintes arquivos para inserir **suas credenciais pessoais**:

### ✉️ devops-app/templates/email-secret.yaml

Altere os campos abaixo para o seu próprio e-mail e senha de app do Gmail:

- SPRING_MAIL_USERNAME: seu-email@gmail.com
- SPRING_MAIL_PASSWORD: sua-senha-de-aplicativo

### 🔐 /devops-app/values.yaml

Altere o campo abaixo para o sua propria senha do banco de dados MySQL (campo mysql.auth.rootPassword

- rootPassword: sua-senha-mysql
  
### 💡 Opção 1:  Executar o script de build

chmod +x scripts/build.sh

./scripts/build.sh

*-* Esse script: *-*

- Faz build das imagens (spring-app, email-service, mysql)

- Carrega no Minikube

- Inicia o cluster

- Instala o Helm Chart

### 💡 Opção 2: Execução Manual (passo a passo)

Siga os passos abaixo caso queira realizar tudo manualmente:

- a) Iniciar o Minikube
- 
```bash
minikube start

- b) Fazer build das imagens Docker

Certifique-se de estar no diretório correto do projeto:

bash'
# Build do email-service (dentro da pasta email-service)
docker build -t email-service:latest ./email-service

# Build do mysql customizado (dentro da pasta mysql)
docker build -t custom-mysql:latest ./mysql

# Build da aplicação Spring Boot (Dockerfile está na raiz)
docker build -t spring-app:latest .
'

- c) Carregar as imagens no Minikube
  
bash'
minikube image load spring-app:latest
minikube image load email-service:latest
minikube image load custom-mysql:latest
'

- d) Instalar com Helm

bash'
helm install devops-app ./devops-app --wait
'

- e) Verificar os pods

bash'
kubectl get pods
'

- f) Executar o tunnel do Minikube

Em um novo terminal:

bash'
minikube tunnel
'

### 4. Acessar a aplicação
Antes, adicione no seu arquivo hosts:

127.0.0.1 k8s.local

🌐 Acesse: http://k8s.local

📧 Para testar o envio de e-mails, use o endpoint /email
