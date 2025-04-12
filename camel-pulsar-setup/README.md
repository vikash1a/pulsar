- [X] Spin up pulsar infra in docker 
- Create a producer 
- Create a camel pulsar consumer \
- Dashboard
- ```docker run -it -d -p 9528:9528 \
  --network="host" \
  --name pulsar-manager \
  apachepulsar/pulsar-manager:latest
```