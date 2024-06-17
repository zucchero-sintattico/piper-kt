git clone https://github.com/prometheus-operator/kube-prometheus.git

cd kube-prometheus

# Create the namespace and CRDs, and then wait for them to be availble before creating the remaining resources
kubectl create -f manifests/setup

# Wait until the "servicemonitors" CRD is created. The message "No resources found" means success in this context.
until kubectl get servicemonitors --all-namespaces ; do date; sleep 1; echo ""; done

kubectl create -f manifests/

# kubectl --namespace monitoring port-forward svc/prometheus-k8s 9090
# kubectl --namespace monitoring port-forward svc/grafana 3000