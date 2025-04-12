package org.example

import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import java.nio.charset.Charset

fun main() {
    println("Hello World!")
    val client = PulsarClient.builder()
        .serviceUrl("pulsar://localhost:6650")
        .build()
    val producer: Producer<ByteArray> = client.newProducer()
        .topic("my-topic")
        .create()
    var i = 0
    while(true){
        i+=1
        produceMessage(producer, i)
        Thread.sleep(1000)
    }

}

fun produceMessage(producer: Producer<ByteArray>, i: Int){

    // Send a message
    val message = "Hello, Pulsar! - $i".toByteArray(Charset.defaultCharset())
    producer.send(message)

    println(message.toString(Charset.defaultCharset()))
}
