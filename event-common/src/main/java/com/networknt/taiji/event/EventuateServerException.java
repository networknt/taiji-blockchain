package com.networknt.taiji.event;

public class EventuateServerException extends EventuateException {

  public EventuateServerException() {
    super("An internal server error occurred");
  }
}
