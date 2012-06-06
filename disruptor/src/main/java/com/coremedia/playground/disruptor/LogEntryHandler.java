package com.coremedia.playground.disruptor;
import com.lmax.disruptor.EventHandler;

public class LogEntryHandler implements EventHandler<LogEntry>
{

  public LogEntryHandler()
  {
  }

  public void onEvent(final LogEntry logEntry, final long sequence, final boolean endOfBatch) throws Exception
  {
    System.out.println(logEntry.text);
  }

}