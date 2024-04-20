package org.example

import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message
import java.nio.charset.Charset

class LocalPulsarClient{
    fun test(){
        val client = PulsarClient.builder()
            .serviceUrl("pulsar://localhost:6650")
            .build()
        val producer: Producer<ByteArray> = client.newProducer()
            .topic("my-topic")
            .create()

        // Send a message
        producer.send("Hello, Pulsar!".toByteArray(Charset.defaultCharset()))

        // Create a consumer
        val consumer: Consumer<ByteArray> = client.newConsumer()
            .topic("my-topic")
            .subscriptionName("my-subscription")
            .subscribe()

        // Receive a message
        val message: Message<ByteArray> = consumer.receive()

        // Print the message
        println(message.value.toString(Charset.defaultCharset()))

        // Close the client
        client.close()
    }
}