#!/bin/bash

echo "Iniciando a implantação da aplicação no Minikube..."

# 1. Iniciar Minikube (se não estiver rodando)
echo "Verificando o status do Minikube..."
minikube status > /dev/null 2>&1
if [ $? -ne 0 ]; then
  echo "Minikube não está rodando. Iniciando Minikube..."
  minikube start --driver=docker || { echo "Falha ao iniciar Minikube. Verifique sua instalação."; exit 1; }
  echo "Minikube iniciado."
else
  echo "Minikube já está rodando."
fi

# 2. Conectar ao ambiente Docker do Minikube
echo "Conectando ao ambiente Docker do Minikube..."
eval $(minikube docker-env) || { echo "Falha ao conectar ao ambiente Docker do Minikube."; exit 1; }
echo "Ambiente Docker do Minikube configurado."

# 3. Build das imagens Docker
echo "Construindo imagens Docker..."

# Navega para o diretório da aplicação principal e constrói a imagem
echo "Construindo imagem spring-app..."
docker build -t spring-app:latest . || { echo "Falha ao construir imagem spring-app."; exit 1; }

# Navega para o diretório do email-service e constrói a imagem
echo "Construindo imagem email-service..."
docker build -t email-service:latest ./email-service || { echo "Falha ao construir imagem email-service."; exit 1; }

# Navega para o diretório do mysql e constrói a imagem (se tiver um Dockerfile customizado)
echo "Construindo imagem mysql-db (se aplicável)..."
# Se você usa a imagem oficial do MySQL, pode pular esta linha
# docker build -t mysql-db:latest ./mysql || { echo "Falha ao construir imagem mysql-db."; exit 1; }

echo "Imagens Docker construídas no ambiente do Minikube."

# 4. Sair do ambiente Docker do Minikube
echo "Desconectando do ambiente Docker do Minikube..."
eval $(minikube docker-env -u) || { echo "Falha ao desconectar do ambiente Docker do Minikube."; exit 1; }
echo "Ambiente Docker do Minikube restaurado para o padrão."

# 5. Habilitar o addon Ingress no Minikube
echo "Habilitando addon Ingress no Minikube..."
minikube addons enable ingress || { echo "Falha ao habilitar addon Ingress."; exit 1; }
echo "Addon Ingress habilitado."

# 6. Atualizar o arquivo /etc/hosts (Requere privilégios de superusuário)
echo "Atualizando /etc/hosts para k8s.local..."
MINIKUBE_IP=$(minikube ip)
HOSTS_ENTRY="$MINIKUBE_IP k8s.local"
if grep -q "k8s.local" /etc/hosts; then
  sudo sed -i "/k8s.local/c\\$HOSTS_ENTRY" /etc/hosts || { echo "Falha ao atualizar /etc/hosts."; exit 1; }
  echo "Entrada k8s.local atualizada em /etc/hosts."
else
  echo "$HOSTS_ENTRY" | sudo tee -a /etc/hosts > /dev/null || { echo "Falha ao adicionar entrada em /etc/hosts."; exit 1; }
  echo "Entrada k8s.local adicionada em /etc/hosts."
fi

# 7. Instalar ou atualizar o Helm Chart
echo "Verificando se o Helm Chart 'my-app' já existe..."
if helm status my-app &> /dev/null; then
  echo "Helm Chart 'my-app' encontrado. Atualizando..."
  helm upgrade my-app devops-app-chart || { echo "Falha ao atualizar Helm Chart."; exit 1; }
else
  echo "Helm Chart 'my-app' não encontrado. Instalando..."
  helm install my-app devops-app-chart || { echo "Falha ao instalar Helm Chart."; exit 1; }
fi
echo "Helm Chart implantado."

echo "Verificando o status dos recursos Kubernetes..."
kubectl get all -l app=spring-app # Exemplo para verificar apenas sua app
kubectl get all -l app=email-service
kubectl get all -l app=mysql
kubectl get ingress

echo "Implantação concluída! A aplicação deverá estar acessível em http://k8s.local"
echo "Pode levar alguns minutos para todos os pods estarem prontos."
echo "Para monitorar os pods: kubectl get pods -w"