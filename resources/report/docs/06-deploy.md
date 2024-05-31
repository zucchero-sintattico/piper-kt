# Deployment Overview

The deployment process is managed by a shell script named `deploy.sh`. This script is responsible for applying the Kubernetes configuration files to the Kubernetes cluster.

## Dependencies

The deployment process relies on several dependencies:

1. **Kubernetes Cluster**: A running Kubernetes cluster is required. This can be a local development cluster (like minikube or kind).

2. **kubectl**: The `kubectl` command-line tool must be installed and configured to interact with the Kubernetes cluster. This tool is used by the `deploy.sh` script to apply the configuration files.

3. **Helm**: Helm, the package manager for Kubernetes, is used to manage the deployment of certain components, such as MongoDB and Strimzi Kafka Operator.

## Operator Deployment

By operator, we mean software that extends the functionalities of Kubernetes, allowing the definition of new custom resource types. These custom resources can be used to define the necessary resources for the application.

The following operators have been used for resource management:

- **Strimzi Kafka Operator**: for managing Kafka. Operator Helm chart [here](https://github.com/strimzi/strimzi-kafka-operator/tree/main/helm-charts/helm3/strimzi-kafka-operator).
- **MongoDB Operator**: for managing MongoDB. Operator Helm chart [here](https://github.com/mongodb/helm-charts/tree/6ddf86b1b00cdd807840de36fc97b91466ee6981/charts/community-operator).

![Operator Deployment](public/schema-Operator.drawio.svg)

## Ingress Controller

For managing incoming traffic,has been used the NGINX Ingress Controller. This component was installed using Helm. The Ingress Controller is configured to handle incoming traffic to the services exposed by the application. Ingress Controller Helm chart [here](https://github.com/kubernetes/ingress-nginx/tree/main/charts/ingress-nginx).

## Resources

The resources needed for the application have been defined through Kubernetes configuration files. After various tests, it was found that to run the system smoothly without considering replicas, at least 32GB of RAM is required.

## Dependencies between Components

The various pods are dependent on each other; for example, the various Micronaut microservices cannot send messages without Kafka. However, for Kubernetes, is not necessary that the pods starting in this order; Kubernetes handles the startup of pods independently and restarts them in case of crashes. This makes the deployment simpler and more resilient to component crashes as described in the [documentation](https://kubernetes.io/docs/concepts/overview/).

## Structure

![Services Structure](public/schema-Global%20Structure%20Services.drawio.svg)

## Deployment Steps

To deploy the application, follow these steps:

### Prerequisites

1. Install `kubectl` to interact with the Kubernetes cluster.
2. Install `helm`.
3. Ensure that the Kubernetes cluster is running. For example, if using minikube, run the command 

```bash
$ minikube start --cpus 8 --memory 32g
```

### Launch the Deployment

1. Clone the repository with the command

```bash
$ git clone https://github.com/zucchero-sintattico/piper-kt.git
```

2. Navigate to the folder

```bash
$ cd piper-kt
```

3. Run the deploy command to start the deployment process.

```bash
$ ./deploy.sh
```

The deployment process may take several minutes depending on the cluster's performance and/or internet connection.
At the end of the process, the application will be available at [http://localhost:8080](http://localhost:8080).

> **Note**: If ingress-nginx-controller service takes too long to start, you can retry the port forwarding command with the following command:
> ```bash
> $ kubectl port-forward -n ingress-nginx svc/ingress-nginx-controller 8080:80

If the deployment process is successful, you should see the following pods running with the command:

```bash
$ kubectl get pods -n piper-kt
```

```Shell
NAME                                        READY   STATUS     RESTARTS    AGE
cluster-broker-0                            1/1     Running    ...         ...
cluster-controller-1                        1/1     Running    ...         ...
cluster-entity-operator-xxx                 2/2     Running    ...         ...
friendships-mongo-0                         2/2     Running    ...         ...
friendships-service-xxx                     1/1     Running    ...         ...
frontend-service-xxx                        1/1     Running    ...         ...
mongo-operator-service-xxx                  1/1     Running    ...         ...
multimedia-mongo-0                          1/2     Running    ...         ...
multimedia-service-xxx                      1/1     Running    ...         ...
notifications-mongo-0                       2/2     Running    ...         ...
notifications-service-xxx                   1/1     Running    ...         ...
servers-mongo-0                             1/2     Running    ...         ...
servers-service-xxx                         1/1     Running    ...         ...
strimzi-cluster-operator-xxx                1/1     Running    ...         ...
users-mongo-0                               1/2     Running    ...         ...
users-service-xxx                           1/1     Running    ...         ...
```

Other useful commands to monitor the cluster status are:

- `kubectl get pods -n piper-kt` to see the running pods.
- `kubectl get services -n piper-kt` to see the exposed services.
- `kubectl describe ingress` to see the ingress configurations.
