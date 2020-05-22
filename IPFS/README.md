# IPFS 
GKS \
IPFS Kubernetes \
https://kubernetes.io/ \
https://ipfs.io/ \
https://cluster.ipfs.io/


<b>CoreDNS</b> \
https://github.com/coredns/deployment/tree/master/kubernetes
```
$ ./deploy.sh | kubectl apply -f -
$ kubectl scale deployment kube-dns-autoscaler --replicas=0 -n kube-system
$ kubectl scale deployment kube-dns --replicas=0 -n kube-system  
$ kubectl scale deployment coredns --replicas=2 -n kube-system 
```

<b>Guide:</b> \
https://cluster.ipfs.io/documentation/guides/k8s/

<b>Run:</b>
```
$ ipfs.sh
or
$ kubectl apply -k ipfs
```

<b>Config:</b> \
swarm.key in configMap.yaml \
https://github.com/Kubuxu/go-ipfs-swarm-key-gen


BOOTSTRAP_PEER_ID \
BOOTSTRAP_PEER_PRIV_KEY \
https://github.com/whyrusleeping/ipfs-key


CLUSTER_SECRET 
```
$ od  -vN 32 -An -tx1 /dev/urandom | tr -d ' \n' | base64
```

CLUSTER_REPLICATION_FACTOR_MIN \
CLUSTER_REPLICATION_FACTOR_MAX \
in web.yaml

Create new files:
```
$ base64 /dev/urandom | head -c 10000000 > file.txt
```
