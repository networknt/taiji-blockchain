package com.networknt.taiji.event;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

class EventHandlerDispatchedEventReturningCompletableFuture implements EventHandler {
  private final Method method;
  private final Object eventHandler;

  public EventHandlerDispatchedEventReturningCompletableFuture(Method method, Object eventHandler) {
    this.method = method;
    this.eventHandler = eventHandler;
  }

  @Override
  public String toString() {
    return "EventHandlerDispatchedEventReturningCompletableFuture{" +
            "method=" + method +
            ", eventHandler=" + eventHandler +
            '}';
  }

  @Override
  public Class<Event> getEventType() {
    return EventHandlerProcessorUtil.getEventClass(method);
  }

  @Override
  public CompletableFuture<?> dispatch(DispatchedEvent<Event> de) {
    try {
      return (CompletableFuture<?>) method.invoke(eventHandler, de);
    } catch (Throwable e) {
      return CompletableFutureUtil.failedFuture(e);
    }
  }

}
