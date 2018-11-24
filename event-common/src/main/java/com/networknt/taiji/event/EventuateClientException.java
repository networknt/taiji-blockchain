package com.networknt.taiji.event;

public class EventuateClientException extends EventuateException {

  public EventuateClientException() {
  }

  public EventuateClientException(Throwable t) {
    super(t);
  }

  public EventuateClientException(String m) {
    super(m);
  }
}
