package com.coremedia.playground.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class BackgroundLogger {
  private static final int ENTRIES = (int) Math.pow(2, 5);

  private static final AtomicLong aLong1 = new AtomicLong();
  private static final AtomicLong aLong2 = new AtomicLong();

  private final ExecutorService executorService;
  private final Disruptor<LogEntry> disruptor;
  private final RingBuffer<LogEntry> ringBuffer;

  BackgroundLogger() {
    executorService = Executors.newCachedThreadPool();
    disruptor = new Disruptor<LogEntry>(LogEntry.FACTORY, ENTRIES, executorService);
    disruptor.handleEventsWith(new LogEntryHandler());
    disruptor.start();
    ringBuffer = disruptor.getRingBuffer();
  }

  public void log(String text) {
    final long sequence = ringBuffer.next();
    final LogEntry logEntry = ringBuffer.get(sequence);

    logEntry.time = System.currentTimeMillis();
    logEntry.level = 1;
    logEntry.text = text;

    ringBuffer.publish(sequence);
  }

  public void stop() {
    disruptor.shutdown();
    executorService.shutdownNow();
  }

  public static void main(String[] args) {
    final BackgroundLogger logger1 = new BackgroundLogger();
    final BackgroundLogger logger2 = new BackgroundLogger();

    Runnable logRunner1 = new Runnable() {
      @Override
      public void run() {
        while (true) {
          long andIncrement = aLong1.getAndIncrement();
          if (andIncrement % 1000000 == 0)
            logger1.log("Logger 1: " + andIncrement);
        }
      }
    };

    Runnable logRunner2 = new Runnable() {
      @Override
      public void run() {
        while (true) {
          long andIncrement = aLong2.getAndIncrement();
          if (andIncrement % 1000000 == 0)
            logger2.log("Logger 2: " + andIncrement);
        }
      }
    };

    new Thread(logRunner1).start();
    new Thread(logRunner2).start();
  }
}
