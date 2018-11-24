package com.networknt.taiji.event;

public class EventuateCommandProcessingFailedException extends EventuateClientException {
  public EventuateCommandProcessingFailedException(Throwable t) {
    super(t);
  }
}
