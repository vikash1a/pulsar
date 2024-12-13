package org.example

import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import org.apache.pulsar.client.api.SubscriptionType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PulsarContainer
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PulsarProducerTest{
    private lateinit var pulsarClient: PulsarClient
    private lateinit var pulsarContainer: PulsarContainer
    private lateinit var pulsarProducer: PulsarProducer

    @BeforeAll
    fun setup() {
        // Start Pulsar container
        pulsarContainer = PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:4.0.1")) // Replace with desired version
        pulsarContainer.start()

        // Initialize Pulsar client
        pulsarClient = PulsarClient.builder()
            .serviceUrl(pulsarContainer.pulsarBrokerUrl) // Get broker URL from container
            .build()

        pulsarProducer = PulsarProducer(pulsarClient)
    }

    @AfterAll
    fun cleanup() {
        pulsarClient.close()
        pulsarContainer.stop()
    }

    @Test
    fun `produce message`(){
        //arrange
        val messageToSend = "hello there from test"
        val consumer = pulsarClient.newConsumer(Schema.STRING)
            .topic("my-topic")
            .subscriptionName("test-subscription")
            .subscriptionType(SubscriptionType.Exclusive)
            .subscribe()

        // act
        pulsarProducer.produceMessage(messageToSend)

        // assert
        val receivedMessage = consumer.receive()
        assertNotNull(receivedMessage)
        assertEquals(messageToSend, receivedMessage.value)

        consumer.acknowledge(receivedMessage)

        consumer.close()
    }
}