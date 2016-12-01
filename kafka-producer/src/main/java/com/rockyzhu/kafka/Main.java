package com.rockyzhu.kafka;

/**
 * Have ASyncProducer running
 */
public class Main {
  public static void main(String[] args) {
    ASyncProducer aSyncProducer = new ASyncProducer("our-first-topic");
    aSyncProducer.start();
  }
}
