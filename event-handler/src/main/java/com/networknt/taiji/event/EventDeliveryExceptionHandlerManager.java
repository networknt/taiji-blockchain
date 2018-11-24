package com.networknt.taiji.event;

public interface EventDeliveryExceptionHandlerManager {

  EventDeliveryExceptionHandlerWithState getEventHandler(Throwable t);


}
