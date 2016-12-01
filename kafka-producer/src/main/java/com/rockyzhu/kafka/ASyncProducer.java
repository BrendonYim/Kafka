package com.rockyzhu.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * Created by hozhu on 11/30/16.
 */
public class ASyncProducer extends Thread {

  private final String _topic;
  private final KafkaProducer<Integer, String> _producer;

  public ASyncProducer(String topic) {
    _topic = topic;
    Properties properties = new Properties();
    properties.put("bootstrap.servers",  "localhost:9092");
    properties.put("client.id", "DemoProducer");
    properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    _producer = new KafkaProducer<>(properties);
  }

  public void run() {
    int messageNum = 1;
    while (true) {
      String message = "Message_" + messageNum;
      long startTime = System.currentTimeMillis();
      _producer.send(new ProducerRecord<>(_topic, messageNum, message), new ASyncProducerCallback(message, startTime));
      messageNum++;
    }
  }

  private class ASyncProducerCallback implements Callback {

    private final String _message;
    private final long _startTime;

    public ASyncProducerCallback(String message, long startTime) {
      _message = message;
      _startTime = startTime;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
      if (metadata != null) {
        long endTime = System.currentTimeMillis();
        System.out.println(_message + " sent to partition " + metadata.partition() + " at: " + _startTime + " done at: " + endTime);
      } else {
        // ignore exception
      }
    }
  }
}
