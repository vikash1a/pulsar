package org.example

import org.apache.pulsar.client.api.*
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure setup and teardown can share context
class PulsarLocalITest {

    private lateinit var pulsarClient: PulsarClient
    private val serviceUrl = "pulsar://localhost:6650" // Replace with your Pulsar instance URL
    private val topicName = "test-topic"

    @BeforeAll
    fun setup() {
        pulsarClient = PulsarClient.builder()
            .serviceUrl(serviceUrl)
            .build()
    }

    @AfterAll
    fun cleanup() {
        pulsarClient.close()
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

        val messageToSend = "Hello, Pulsar from Kotlin!"

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
