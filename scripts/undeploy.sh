#!/bin/bash


kubectl delete secrets sh.helm.release.v1.friendships-service-mongo.v1
kubectl delete secrets sh.helm.release.v1.multimedia-service-mongo.v1
kubectl delete secrets sh.helm.release.v1.servers-service-mongo.v1
kubectl delete secrets sh.helm.release.v1.users-service-mongo.v1 

# Delete Helm releases
helm ls --all --short | xargs -L1 helm uninstall

# Delete resources
kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission

# Delete the namespace
kubectl delete namespace piper-kt
kubectl delete namespace ingress-nginx