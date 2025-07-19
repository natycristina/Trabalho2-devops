#!/bin/bash

set -e  # Para o script se algum comando falhar

echo "🔧 Iniciando Minikube..."
minikube start

echo "🐳 Construindo imagens Docker..."

docker build -t email-service:latest ../email-service
docker build -t custom-mysql:latest ../mysql
docker build -t spring-app:latest ..

echo "📦 Carregando imagens no Minikube..."
minikube image load spring-app:latest
minikube image load email-service:latest
minikube image load mysql:custom

echo "🚀 Instalando aplicação com Helm..."
helm install devops-app ../devops-app --wait

echo "✅ Aplicação implantada! Verificando pods..."
kubectl get pods

echo "🌐 Para acessar os serviços (LoadBalancer), execute em um novo terminal:"
echo "👉 minikube tunnel"
