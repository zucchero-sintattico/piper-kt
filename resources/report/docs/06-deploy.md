# Deployment Overview
The deployment process is managed by a shell script named `deploy.sh`. This script is responsible for applying the Kubernetes configuration files to the Kubernetes cluster. The configuration files define the desired state of the cluster, including which applications to run, network settings, and storage options.

## Dependencies
The deployment process relies on several dependencies:

1. **Kubernetes Cluster**: A running Kubernetes cluster is required. This can be a local development cluster (like minikube or kind).

2. **kubectl**: The `kubectl` command-line tool must be installed and configured to interact with the Kubernetes cluster. This tool is used by the `deploy.sh` script to apply the configuration files.

3. **Helm**: Helm, the package manager for Kubernetes, is used to manage the deployment of certain components, such as MongoDB and Strimzi Kafka Operator.

4. **Configuration Files**: The `kubernetes/` directory should contain all the necessary Kubernetes configuration files. These files may define Deployments, Services, Persistent Volumes, ConfigMaps, and other Kubernetes resources.

## Operator Deployment

Con operatore si intende un software che estende le funzionalità di Kubernetes, permettendo di definire nuovi tipi di risorse personalizzate. Queste risorse personalizzate possono essere utilizzate per definire le risorse necessarie per l'applicazione, come ad esempio un cluster Kafka o un'istanza di MongoDB.

Sono stati utilizzati i seguenti operatori per la gestione delle risorse:
- **Strimzi Kafka Operator**: per la gestione di Kafka. Operator Helm chart [here](https://github.com/strimzi/strimzi-kafka-operator/tree/main/helm-charts/helm3/strimzi-kafka-operator).
- **MongoDB Operator**: per la gestione di MongoDB. Operator Helm chart [here](https://github.com/mongodb/helm-charts/tree/6ddf86b1b00cdd807840de36fc97b91466ee6981/charts/community-operator).


## Ingress Controller
Per la gestione del traffico in ingresso è stato utilizzato l'Ingress Controller di NGINX. Questo componente è stato installato tramite Helm. L'Ingress Controller è stato configurato per gestire il traffico in ingresso verso i servizi esposti dall'applicazione. Ingess Controller Helm chart [here](https://github.com/kubernetes/ingress-nginx/tree/main/charts/ingress-nginx).

## Risorse
Le risorse necessarie all'applicazione sono state definite tramite file di configurazione Kubernetes. Dopo varie prove, è stato costatato che per far girare il sistema in maniera fluida senza considerare repliche, è necessario avere almeno 32GB di RAM a disposizione.

## Dipendenze tra i componenti
I vari pod tra di loro sono dipeendenti, per esempio i vari microservizi micronaut non possono inviare messaggi senza che Kafka sia in esecuzione. Per il funzionamento di kubenetes però non è necessario che i pods siano avviati seguqndo quest'ordine, Kubernetes si occupa di avviare i pods in maniera indipendente e di riavviarli in caso di crash. In questo modo il deploy è più semplice e resistente a crash dei vari componenti come descritto nella [documentaizone](https://kubernetes.io/docs/concepts/overview/).

## Deploy
Per effettuare il deploy dell'applicazione è necessario eseguire i seguenti passaggi:

### Prerequisiti
1. Installare `kubectl` per interagire con il cluster Kubernetes.
2. Installare `helm`.
3. Assicurarsi che il cluster Kubernetes sia in esecuzione. Per esempio se si utilizza minikube, eseguire il comando `minikube start --cpus 8 --memory 32000`.

### Lanciare il deploy
1. clone the repository con il comando `git clone https://github.com/zucchero-sintattico/piper-kt.git`
2. spostarsi nella cartella `cd piper-kt`
3. eseguire il comando `./deploy.sh` per avviare il processo di deploy.

Il processo di deploy può richiedere diversi minuti a seconda delle prestazioni del cluster e della connessione internet.
Alla fine del processo, l'applicazione sarà disponibile all'indirizzo `http://localhost:8080`.

Se il processo di deploy andra a buon fine, sarà possibile vedere i seguenti pod in esecuzione con il comando `kubectl get pods -n piper-kt`:

``` 
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


Altri comandi utili per monitorare lo stato del cluster sono:
- `kubectl get pods -n piper-kt` per vedere i pod in esecuzione.
- `kubectl get services -n piper-kt` per vedere i servizi esposti.
- `kubectl describe ingress` per vedere le configurazioni di ingress.

