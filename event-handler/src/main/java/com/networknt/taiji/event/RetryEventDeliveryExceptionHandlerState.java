package com.networknt.taiji.event;

public class RetryEventDeliveryExceptionHandlerState implements EventDeliveryExceptionHandlerState {
  public int retries;

  public RetryEventDeliveryExceptionHandlerState(Throwable t) {
    this.retries = 0;
  }

}
