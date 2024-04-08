#!/bin/bash

helm ls --all --short | xargs -L1 helm delete

kubectl delete -f auth.yml
kubectl apply -f auth.yml

# shellcheck disable=SC2093
eval $(minikube docker-env)
./gradlew dockerBuild
helm install  friendship-service  piper-chart  --values  friendship-service/values.yaml

