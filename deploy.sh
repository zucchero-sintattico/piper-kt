#!/bin/bash


kubectl create namespace piper-kt
kubectl apply -f kubernetes/auth.yml
helm repo add mongodb https://mongodb.github.io/helm-charts
helm install strimzi-cluster-operator --set replicas=1 --set resources.limits.cpu=700 oci://quay.io/strimzi-helm/strimzi-kafka-operator --namespace piper-kt
kubectl apply -f kubernetes/kafka.yml --namespace piper-kt
helm install --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt \
    --set watchNamespaces=piper-kt\
    --values kubernetes/operator-values.yaml


microservice_list=("friendships-service" "users-service" "servers-service" "multimedia-service")
for microservice in "${microservice_list[@]}"
do
  helm install $microservice kubernetes/helm-chart/piper-chart --values $microservice/helm-values/micronaut-values.yaml
  helm install "${microservice}-mongo" kubernetes/helm-chart/mongo-for-operator --namespace piper-kt\
      --values $microservice/helm-values/mongo-values.yaml
done

helm install notifications-service kubernetes/helm-chart/mongo-for-operator --namespace piper-kt\
    --values notifications-service/helm-values/mongo-values.yaml

kubectl apply -f kubernetes/notifications.yml --namespace piper-kt

kubectl apply -f kubernetes/frontend.yml --namespace piper-kt

helm install nginx-ingress-controller kubernetes/helm-chart/piper-ingress --namespace piper-kt

kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/aws/deploy.yaml

kubectl get pods -n piper-kt
kubectl get deploy -n piper-kt
kubectl get svc -n piper-kt

kubectl wait pod -l app.kubernetes.io/component=controller --for=condition=Ready -n ingress-nginx

kubectl port-forward svc/ingress-nginx-controller 8080:80 -n ingress-nginx


