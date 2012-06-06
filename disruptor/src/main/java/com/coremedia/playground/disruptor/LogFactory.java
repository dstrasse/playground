package com.coremedia.playground.disruptor;
import com.lmax.disruptor.EventFactory;

class LogEntry
{
  public static final EventFactory<LogEntry> FACTORY = new EventFactory<LogEntry>()
  {
    public LogEntry newInstance()
    {
      return new LogEntry();
    }
  };

  long time;
  int level;
  String text;
}