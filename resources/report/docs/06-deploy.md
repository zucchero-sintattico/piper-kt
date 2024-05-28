# Deploy

## Kubernetes

Per assiurare un deploy su Kubernetes, senza dulpicare eccessivamente i file di configurazione, si è scelto di utilizzare [Helm](https://helm.sh/). Helm è un package manager per Kubernetes che permette di definire e installare applicazioni Kubernetes. In particilare sono stati creati tre chart Helm: 
- uno per tutti i micro servizi che utilizano micronaut (piper-chart)
- uno per la crezione di database mongo, che si collegano automaticamente ad un operatore (mongo-for-operator)
- uno per la creazione dell' ingress controller (piper-ingress)

Ogni microservizio al sui interno contiene file con i valori da impostare per il deploy, per evitare di duplicare i file di configurazione.

