mvn clean install
docker build . -t ankita1793/driver
docker push ankita1793/driver
kubectl delete deployment driver-service
kubectl apply -f src/main/docker/postgres-configmap.yml
kubectl apply -f src/main/docker/postgres-credentials.yml
kubectl apply -f src/main/docker/postgres-service.yml
kubectl apply -f src/main/docker/app-deployment.yml
kubectl get pods