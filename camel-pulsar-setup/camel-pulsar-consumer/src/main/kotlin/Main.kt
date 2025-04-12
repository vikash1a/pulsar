package org.example

import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message
import org.apache.pulsar.client.api.PulsarClient
import java.nio.charset.Charset
import org.apache.camel.CamelContext
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.impl.DefaultCamelContext

fun main() {
    println("Hello World!")
//    startPulsarConsumer()
    startCamelConsumer()
}

fun startPulsarConsumer(){
    val client = PulsarClient.builder()
        .serviceUrl("pulsar://localhost:6650")
        .build()
    // Create a consumer
    val consumer: Consumer<ByteArray> = client.newConsumer()
        .topic("my-topic")
        .subscriptionName("my-subscription")
        .subscribe()

    while(true){
        Thread.sleep(2000)
        consume(consumer)
    }
//    client.close()
}

fun consume(consumer: Consumer<ByteArray>){
    // Receive a message
    val message: Message<ByteArray> = consumer.receive()

    // Print the message
    println(message.value.toString(Charset.defaultCharset()))
}


fun startCamelConsumer() {
    val camelContext: CamelContext = DefaultCamelContext()

    camelContext.addRoutes(object : RouteBuilder() {
        override fun configure() {
            val serviceUrl = "pulsar://localhost:6650"
            from("pulsar:persistent/public/default/my-topic?subscriptionName=my-subscription&serviceUrl=${serviceUrl}&numberOfConsumers=1&numberOfConsumerThreads=100&messageListener=false")
                .process { exchange ->
                    val msg = exchange.getIn().getBody(String::class.java)
                    println(">>> Camel Received: $msg")
                    Thread.sleep(10000)
                    exchange.getIn().setHeader("CamelPulsarMessageAcknowledged", true)
                }
        }
    })

    // Start the Camel Context
    camelContext.start()
}

