#!/bin/bash

minikube delete
minikube config set memory 6000
minikube config set cpus 4
minikube start


eval $(minikube docker-env)
#

#create namespace
kubectl create namespace piper-kt
kubectl apply -f kubernetes/auth.yml
helm repo add mongodb https://mongodb.github.io/helm-charts

kubectl create -f 'https://strimzi.io/install/latest?namespace=piper-kt' -n piper-kt
kubectl apply -f kubernetes/kafka.yml --namespace piper-kt     

helm install --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt \
    --set watchNamespaces=piper-kt\
    --values kubernetes/operator-values.yaml


./gradlew dockerBuild
#microservice_list=("friendships-service" "users-service" "servers-service" "multimedia-service")
microservice_list=("users-service")

for microservice in "${microservice_list[@]}"
do
  helm install $microservice kubernetes/helm-chart/piper-chart --values $microservice/helm-values/micronaut-values.yaml

  helm install $microservice kubernetes/helm-chart/mongo-for-operator --namespace piper-kt\
      --values $microservice/helm-values/mongo-values.yaml

done

kubectl apply -f kubernetes/nginx-ingress-controller.yaml --namespace piper-kt

minikube addons enable ingress

minikube tunnel


#https://github.com/mongodb/helm-charts/tree/d843c293bf86653d4b9193ab245f04934c93a722/charts/



#kubectl port-forward services/friendships-mongo-svc -n piper-kt  27017:27017
#Connection String: "mongodb://friendships-mongo:password@localhost:27017/?authSource=admin&authMechanism=SCRAM-SHA-256&directConnection=true"

#expose nginx service
#kubectl port-forward services/services-nginx-ingress-controller -n piper-kt  8080:80

