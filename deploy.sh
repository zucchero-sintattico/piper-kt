#!/bin/bash

minikube delete
minikube start


eval $(minikube docker-env)
./gradlew dockerBuild

helm repo add mongodb https://mongodb.github.io/helm-charts
#https://github.com/mongodb/helm-charts/tree/d843c293bf86653d4b9193ab245f04934c93a722/charts/
helm upgrade -i friendship-service  helm-chart/piper-chart  --values  friendship-service/helm-values/micronaut-values.yaml
#helm upgrade -i friendship-service helm-chart/community-operator --namespace piper-kt --values friendship-service/helm-values/db-values.yaml
helm upgrade -i --set image.tag=0.9.0-arm64 community-operator mongodb/community-operator --namespace piper-kt --set watchNamespaces=piper-kt
helm upgrade -i friendship-service  helm-chart/mongo-for-operator --namespace piper-kt