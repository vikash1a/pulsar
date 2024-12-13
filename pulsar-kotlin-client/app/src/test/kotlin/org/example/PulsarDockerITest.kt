package org.example

import org.apache.pulsar.client.api.*
import org.junit.jupiter.api.*
import org.testcontainers.containers.PulsarContainer
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PulsarDockerITest {

    private lateinit var pulsarClient: PulsarClient
    private lateinit var pulsarContainer: PulsarContainer
    private val topicName = "test-topic"

    @BeforeAll
    fun setup() {
        // Start Pulsar container
        pulsarContainer = PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:4.0.1")) // Replace with desired version
        pulsarContainer.start()

        // Initialize Pulsar client
        pulsarClient = PulsarClient.builder()
            .serviceUrl(pulsarContainer.pulsarBrokerUrl) // Get broker URL from container
            .build()
    }

    @AfterAll
    fun cleanup() {
        pulsarClient.close()
        pulsarContainer.stop()
    }

    @Test
    fun `should send and receive a message`() {
        val producer = pulsarClient.newProducer(Schema.STRING)
            .topic(topicName)
            .create()

        val consumer = pulsarClient.newConsumer(Schema.STRING)
            .topic(topicName)
            .subscriptionName("test-subscription")
            .subscriptionType(SubscriptionType.Exclusive)
            .subscribe()

        val messageToSend = "Hello, Pulsar with Docker!"

        // Produce a message
        producer.send(messageToSend)

        // Consume the message
        val receivedMessage = consumer.receive()
        assertNotNull(receivedMessage)
        assertEquals(messageToSend, receivedMessage.value)

        // Acknowledge the message
        consumer.acknowledge(receivedMessage)

        // Clean up
        producer.close()
        consumer.close()
    }
}
