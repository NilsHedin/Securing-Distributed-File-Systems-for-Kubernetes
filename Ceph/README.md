# Ceph

GKS \
Ceph Kubernetes \
https://ceph.io/ \
https://rook.io/docs/rook/v1.3/ceph-storage.html\

<b>Deployment:</b> 
```
$ kubectl create -f common.yaml
$ kubectl create -f operator.yaml
WAIT
$ kubectl create -f cluster-on-pvc.yaml
WAIT
$ kubectl create -f object-test.yaml
WAIT
$ kubectl create -f storageclass-bucket-delete.yaml
$ kubectl create -f object-bucket-claim-delete.yaml
$ kubectl create -f rgw-external.yaml

Access and secret key
$ kubectl -n default get secret ceph-delete-bucket -o yaml | grep AWS_ACCESS_KEY_ID | awk '{print $2}' | base64 --decode
$ kubectl -n default get secret ceph-delete-bucket -o yaml | grep AWS_SECRET_ACCESS_KEY | awk '{print $2}' | base64 --decode
```
