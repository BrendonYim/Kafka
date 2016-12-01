package com.rockyzhu.kafka;

/**
 * Have Consumer running
 */
public class Main {
  public static void main(String[] args) {
    Consumer consumer = new Consumer("our-first-topic");
    consumer.start();
  }
}
