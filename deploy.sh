#!/bin/bash


#create namespace
kubectl create namespace piper-kt
kubectl apply -f kubernetes/auth.yml
helm repo add mongodb https://mongodb.github.io/helm-charts



helm install strimzi-cluster-operator --set replicas=1 --set resources.limits.cpu=500 oci://quay.io/strimzi-helm/strimzi-kafka-operator --namespace piper-kt
kubectl apply -f kubernetes/kafka.yml --namespace piper-kt
helm install --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt \
    --set watchNamespaces=piper-kt\
    --values kubernetes/operator-values.yaml


#./gradlew dockerBuild
microservice_list=("friendships-service" "users-service" "servers-service" "multimedia-service")
#microservice_list=("users-service")

for microservice in "${microservice_list[@]}"
do
  helm install $microservice kubernetes/helm-chart/piper-chart --values $microservice/helm-values/micronaut-values.yaml

  helm install $microservice kubernetes/helm-chart/mongo-for-operator --namespace piper-kt\
      --values $microservice/helm-values/mongo-values.yaml

done

helm install nginx-ingress-controller kubernetes/helm-chart/piper-ingress --namespace piper-kt


kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/aws/deploy.yaml


sleep 60

kubectl port-forward svc/ingress-nginx-controller 8080:80 -n ingress-nginx


