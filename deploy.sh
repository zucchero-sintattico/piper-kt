#!/bin/bash

kubectl delete --all pods --namespace=atlas-operator
kubectl delete --all pods --namespace=micronaut-k8s

helm ls --all --short | xargs -L1 helm delete

kubectl delete -f auth.yml


# shellcheck disable=SC2093
eval $(minikube docker-env)
#./gradlew dockerBuild

kubectl apply -f auth.yml

helm upgrade -i  friendship-service  piper-chart  --values  friendship-service/values.yaml

#https://github.com/mongodb/helm-charts/tree/d843c293bf86653d4b9193ab245f04934c93a722/charts/atlas-operator
helm upgrade -i atlas-operator\
  --namespace=atlas-operator\
  --create-namespace atlas-operator\
  --values friendship-service/db-values.yaml