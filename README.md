# Spring Microservice K8s [![Build Status](https://travis-ci.org/dhananjay12/spring-microservice-k8s.svg?branch=master)](https://travis-ci.org/dhananjay12/spring-microservice-k8s)

## Build and Dockerize

mvn clean install -Ddocker

## Build and Dockerize but Skip Docker Push

mvn clean install -Ddocker -Ddocker.skip.push

## Copy data.csv to the volume created

### Google Cloud
Login to your google account using gcloud and then set the project and region.
Then set the cluster using following command:

```
gcloud container clusters get-credentials spring-k8s
```

Then copy the file using follwoing command 

```
kubectl cp data.csv <namespace>/<pod-name>:/app/data
```
where /app/data is the mount path of the container.


NOTE:  If you refer the official Google Documentation, you will notice that in ReadWriteMany access type, PersistentVolumes that are backed by Compute Engine persistent disks are not supported. There are alternative solutions for this like Google Filestore or Network File System(NFS).