#!/bin/bash

kubectl delete -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/aws/deploy.yaml

helm delete nginx-ingress-controller kubernetes/helm-chart/piper-ingress --namespace piper-kt

kubectl delete -f kubernetes/frontend.yml --namespace piper-kt

kubectl delete -f kubernetes/notifications.yml --namespace piper-kt

helm delete notifications-service kubernetes/helm-chart/mongo-for-operator --namespace piper-kt

microservice_list=("friendships-service" "users-service" "servers-service" "multimedia-service")
for microservice in "${microservice_list[@]}"
do
  helm delete $microservice kubernetes/helm-chart/piper-chart
  helm delete $microservice kubernetes/helm-chart/mongo-for-operator --namespace piper-kt
done

helm delete --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt 

kubectl delete -f kubernetes/kafka.yml --namespace piper-kt

helm delete strimzi-cluster-operator --set replicas=1 --set resources.limits.cpu=700 oci://quay.io/strimzi-helm/strimzi-kafka-operator --namespace piper-kt

helm repo remove mongodb https://mongodb.github.io/helm-charts
kubectl delete -f kubernetes/auth.yml
kubectl delete namespace piper-kt
