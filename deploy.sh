#!/bin/bash

minikube delete
minikube config set memory 7000
minikube config set cpus 6
minikube start


eval $(minikube docker-env)
#
./gradlew dockerBuild
#create namespace
kubectl create namespace piper-kt
kubectl apply -f auth.yml
helm repo add mongodb https://mongodb.github.io/helm-charts

helm install --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt \
    --set watchNamespaces=piper-kt\
    --values operator-values.yaml

microservice_list=("friendships-service" "users-service" "servers-service")

for microservice in "${microservice_list[@]}"
do
  helm install $microservice helm-chart/piper-chart --values $microservice/helm-values/micronaut-values.yaml

#  helm install $microservice helm-chart/mongo-for-operator --namespace piper-kt\
#      --values $microservice/helm-values/mongo-values.yaml

done


#https://github.com/mongodb/helm-charts/tree/d843c293bf86653d4b9193ab245f04934c93a722/charts/



#kubectl port-forward services/friendships-mongo-svc -n piper-kt  27017:27017
#Connection String: "mongodb://friendships-mongo:password@localhost:27017/?authSource=admin&authMechanism=SCRAM-SHA-256&directConnection=true"