#!/bin/bash

minikube delete
minikube config set memory 2048
minikube config set cpus 4
minikube start


eval $(minikube docker-env)
./gradlew dockerBuild
helm repo add mongodb https://mongodb.github.io/helm-charts
microservice_list=("friendships-service" "users-service" "servers-service")

for microservice in "${microservice_list[@]}"
do
  helm upgrade -i $microservice helm-chart/piper-chart --values $microservice/helm-values/micronaut-values.yaml

  helm upgrade -i --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt \
      --set watchNamespaces=piper-kt\
      --values $microservice/helm-values/db-values.yaml

  helm upgrade -i $microservice helm-chart/mongo-for-operator --namespace piper-kt\
      --values $microservice/helm-values/mongo-values.yaml

done


#https://github.com/mongodb/helm-charts/tree/d843c293bf86653d4b9193ab245f04934c93a722/charts/



#kubectl port-forward services/friendship-mongo-svc -n piper-kt  27017:27017
#Connection String: mongodb://my-user:password@localhost:27017/?authSource=admin&authMechanism=SCRAM-SHA-256&directConnection=true