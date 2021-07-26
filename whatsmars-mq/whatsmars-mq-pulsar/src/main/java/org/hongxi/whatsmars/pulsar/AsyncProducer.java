package org.hongxi.whatsmars.pulsar;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

/**
 * Created by shenhongxi on 2021/7/20.
 */
public class AsyncProducer {

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();

        producer.sendAsync("my-async-message".getBytes()).whenComplete((messageId, throwable) -> {
            System.out.println("Message with ID " + messageId + " successfully sent");
                }
        );
    }
}
