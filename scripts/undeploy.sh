#!/bin/bash

# Set the current context and namespace
cd "$(dirname "$0")/.." || exit

# Delete Helm releases
helm delete strimzi-cluster-operator
helm delete community-operator
helm delete nginx-ingress-controller --namespace nginx-ingress

# Delete microservices and associated MongoDB charts
microservice_list=("friendships-service" "users-service" "servers-service" "multimedia-service")
for microservice in "${microservice_list[@]}"
do
  helm delete "$microservice"
  helm delete "${microservice}-mongo"
done

# Delete resources
kubectl delete -f kubernetes/auth.yml
kubectl delete -f kubernetes/kafka.yml
kubectl delete -f kubernetes/notifications.yml
kubectl delete -f kubernetes/frontend.yml

#wait for resources to be deleted
kubectl wait --for=delete pods --all

# Delete the namespace
kubectl delete namespace piper-kt
kubectl delete namespace ingress-nginx

