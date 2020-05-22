#!/bin/bash
cd deployment/kubernetes
./deploy.sh | kubectl apply -f -
kubectl scale deployment kube-dns-autoscaler --replicas=0 -n kube-system
kubectl scale deployment kube-dns --replicas=0 -n kube-system
kubectl scale deployment coredns --replicas=2 -n kube-system 
cd ..
cd ..
sleep 5
kubectl apply -k ipfs 
