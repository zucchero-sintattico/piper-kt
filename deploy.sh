#!/bin/bash

minikube delete
minikube start


eval $(minikube docker-env)
./gradlew dockerBuild


#https://github.com/mongodb/helm-charts/tree/d843c293bf86653d4b9193ab245f04934c93a722/charts/
helm upgrade -i friendship-service  helm-chart/piper-chart  --values  friendship-service/helm-values/micronaut-values.yaml
helm dependency build helm-chart/community-operator
helm upgrade -i friendship-service helm-chart/community-operator --namespace piper-kt --values friendship-service/helm-values/db-values.yaml