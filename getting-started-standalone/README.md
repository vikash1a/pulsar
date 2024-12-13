```
docker run -it \
-p 6650:6650 \
-p 8080:8080 \
--mount source=pulsardata,target=/pulsar/data \
--mount source=pulsarconf,target=/pulsar/conf \
apachepulsar/pulsar:4.0.1 \
bin/pulsar standalone
```

- Create topic
    - `./bin/pulsar-admin topics create persistent://public/default/first-topic`
- Produce Message
    - `./bin/pulsar-client produce persistent://public/default/first-topic -m "Hello, Pulsar!"`
- Consume Message
    - `./bin/pulsar-client consume persistent://public/default/first-topic -s "subscription-name" -n 10`

