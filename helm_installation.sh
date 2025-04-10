kubectl --insecure-skip-tls-verify apply -f namespace.yml
kubectl config set-context --current --namespace=tinyx --insecure-skip-tls-verify
helm upgrade --install --kube-insecure-skip-tls-verify ingress-nginx ingress-nginx --repo https://kubernetes.github.io/ingress-nginx --namespace tinyx
helm install community-operator mongodb/community-operator --kube-insecure-skip-tls-verify
helm install neo4j neo4j/neo4j --namespace tinyx -f neo4j/neo4jvalues.yml --kube-insecure-skip-tls-verify
kubectl --insecure-skip-tls-verify apply -k .
