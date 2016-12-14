package com.rockyzhu.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.connect.util.ShutdownableThread;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by hozhu on 11/30/16.
 */
public class Consumer extends ShutdownableThread {
  private final KafkaConsumer<Integer, String> _consumer;
  private final String _topic;

  public Consumer(String topic, String groupId) {
    super("KafkaConsumerExample", false);
    _topic = topic;
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
    properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
    properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    _consumer = new KafkaConsumer<>(properties);
  }

  @Override
  public void execute() {
    _consumer.subscribe(Collections.singletonList(_topic));
    while (true) {
      ConsumerRecords<Integer, String> records = _consumer.poll(2000);
      for (ConsumerRecord<Integer, String> record : records) {
        System.out.println("topic:" + _topic + "key: " + record.key() + ", value: " + record.value() + ", offset: " + record.offset());
      }
    }
  }
}
