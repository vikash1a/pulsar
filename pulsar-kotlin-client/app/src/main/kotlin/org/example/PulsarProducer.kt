package org.example

import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import java.nio.charset.Charset

class PulsarProducer(private val pulsarClient: PulsarClient) {
    fun produceMessage(message: String){
        val producer: Producer<String> = pulsarClient.newProducer(Schema.STRING)
            .topic("my-topic")
            .create()

        // Send a message
        producer.send(message)

        producer.close()
    }
}