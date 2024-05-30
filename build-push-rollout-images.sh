#!/bin/bash

set -e

./gradlew clean

for service in "${@:2}"
do
  echo "Building $service"
  ./gradlew clean :$service:dockerfile :$service:buildLayers
  docker buildx build -t zuccherosintattico/piperkt-$service:test --platform=linux/amd64 --push ./$service/build/docker/main/
  ssh $1 "kubectl rollout restart deployment $service -n piper-kt"
done
