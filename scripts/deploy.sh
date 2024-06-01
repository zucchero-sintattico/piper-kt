#!/bin/bash

NAMESPACE="piper-kt"

# Create namespace
kubectl create namespace $NAMESPACE

# Set current context to the namespace
kubectl config set-context --current --namespace=$NAMESPACE

# Apply auth.yml
kubectl apply -f kubernetes/auth.yml

# Add MongoDB Helm repository
helm repo add mongodb https://mongodb.github.io/helm-charts

# Install Strimzi Kafka Operator
helm install strimzi-cluster-operator --set replicas=1 --set resources.limits.cpu=700 oci://quay.io/strimzi-helm/strimzi-kafka-operator

# Apply kafka.yml
kubectl apply -f kubernetes/kafka.yml

# Install MongoDB Community Operator
helm install --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator \
    --set watchNamespaces=$NAMESPACE \
    --values kubernetes/operator-values.yaml

# Install microservices
microservice_list=("friendships-service" "users-service" "servers-service" "multimedia-service")
for microservice in "${microservice_list[@]}"
do
  helm install "$microservice" kubernetes/helm-chart/micronaut-chart \
      --values "$microservice"/helm-values/micronaut-values.yaml
  helm install "${microservice}-mongo" kubernetes/helm-chart/mongo-chart \
      --values "$microservice"/helm-values/mongo-values.yaml
done

# Install notifications-service
helm install notifications-service kubernetes/helm-chart/mongo-for-operator \
    --values notifications-service/helm-values/mongo-values.yaml

# Apply notifications.yml and frontend.yml
kubectl apply -f kubernetes/notifications.yml \
                 -f kubernetes/frontend.yml

# Install nginx-ingress-controller
helm install nginx-ingress-controller kubernetes/helm-chart/ingress-chart

# Apply ingress-nginx deploy.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/aws/deploy.yaml

# Wait for ingress-nginx controller pod to be ready
kubectl wait pod -l app.kubernetes.io/component=controller --for=condition=Ready -n ingress-nginx --timeout=120s

# Get pods, deployments, and services
kubectl get pods
kubectl get deploy
kubectl get svc

echo "Too check the pods' status, run 'kubectl get pods -w'"

# Port forward ingress-nginx-controller service
kubectl port-forward svc/ingress-nginx-controller 8080:80 -n ingress-nginx