package com.rockyzhu.kafka;

/**
 * Have ASyncProducer running
 */
public class Main {
  public static void main(String[] args) {
    ASyncProducerDemo aSyncProducer = new ASyncProducerDemo(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]));
    aSyncProducer.start();
  }
}
