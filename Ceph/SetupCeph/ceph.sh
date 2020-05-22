#!/bin/bash
cd ceph
kubectl create -f common.yaml
kubectl create -f operator.yaml
sleep 30
kubectl create -f cluster-on-pvc.yaml
