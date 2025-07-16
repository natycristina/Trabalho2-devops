#!/bin/bash

# Inicia o Minikube
minikube start

# Build das imagens
docker build -t spring-app:latest .
docker build -t email-service:latest ./email-service
docker build -t mysql:custom ./mysql

# Load das imagens no Minikube
minikube image load spring-app:latest
minikube image load email-service:latest
minikube image load mysql:custom

# Instala com Helm
helm upgrade --install devops-app ./devops-app

# Verifica os pods
kubectl get pods

echo "Abra um novo terminal e execute: minikube tunnel"
