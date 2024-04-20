```
setup -> 
wget https://archive.apache.org/dist/pulsar/pulsar-3.2.2/apache-pulsar-3.2.2-bin.tar.gz
tar xvfz apache-pulsar-3.2.2-bin.tar.gz
cd apache-pulsar-3.2.2
ls -1F

Start -> 
bin/pulsar standalone

Create topic ->
bin/pulsar-admin topics create persistent://public/default/my-topic

Write messge to topic -> 
bin/pulsar-client produce my-topic --messages 'Hello Pulsar!'

Read Message from topic -> 
bin/pulsar-client consume my-topic -s 'my-subscription' -p Earliest -n 0

```
