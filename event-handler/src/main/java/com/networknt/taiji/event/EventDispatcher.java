package com.networknt.taiji.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * EventDispatcher execute event dispatch method based on the input event
 */
public class EventDispatcher {

  private static Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

  private final String subscriberId;
  private final Map<Class<?>, EventHandler> eventTypesAndHandlers;
  private EventDeliveryExceptionHandlerManager eventDeliveryExceptionHandlerManager;

  /**
   * Construct EventDispatcher
   * @param subscriberId defined topic subscriberId
   * @param eventTypesAndHandlers defined event handlers
   * @param eventDeliveryExceptionHandlerManager exception handler manager
   */
  public EventDispatcher(String subscriberId, Map<Class<?>, EventHandler> eventTypesAndHandlers, EventDeliveryExceptionHandlerManager eventDeliveryExceptionHandlerManager) {
    this.subscriberId = subscriberId;
    this.eventTypesAndHandlers = eventTypesAndHandlers;
    this.eventDeliveryExceptionHandlerManager = eventDeliveryExceptionHandlerManager;
  }

  /**
   * Dispatch event to event handler
   * @param de dispatched event
   * @return CompletableFuture
   */
  public CompletableFuture<?> dispatch(DispatchedEvent<Event> de) {
    EventHandler eventHandler = eventTypesAndHandlers.get(de.getEventType());
    if (eventHandler != null) {
      CompletableFuture<Object> result = new CompletableFuture<>();
      dispatchEvent(de, eventHandler, result, Optional.empty());
      return result;
    } else {
      RuntimeException ex = new RuntimeException("No handler for event - subscriberId: " + subscriberId + ", " + de.getEventType());
      logger.error("dispatching failure", ex);
      CompletableFuture completableFuture = new CompletableFuture();
      completableFuture.completeExceptionally(ex);
      return completableFuture;
    }
  }

  private void dispatchEvent(DispatchedEvent<Event> de, EventHandler eventHandler, CompletableFuture<Object> result, Optional<EventDeliveryExceptionHandlerWithState> ehr) {
    if (logger.isDebugEnabled()) {
      logger.debug("Invoking event handler {} {} {}", subscriberId, de, eventHandler);
    }

    CompletableFuture<?> r1 = eventHandler.dispatch(de);

    if (logger.isDebugEnabled()) {
      logger.debug("Invoking event handler {} {} {}", subscriberId, de, eventHandler);
      CompletableFutureUtil.tap(r1, (o, throwable) -> {
        if (throwable == null)
          logger.debug("Invoked event handler {} {} {}", subscriberId, de, eventHandler);
        else
          logger.debug(String.format("Event handler failed %s %s %s", subscriberId, de, eventHandler), throwable);
      });
    }

    r1.handle((o, t) -> {
              if (t == null) {
                result.complete(o);
              } else {
                Throwable t1 = CompletableFutureUtil.unwrap(unwrap(CompletableFutureUtil.unwrap(t)));
                EventDeliveryExceptionHandlerWithState eventDeliveryExceptionHandlerWithState = ehr.orElseGet(() -> eventDeliveryExceptionHandlerManager.getEventHandler(t1));
                eventDeliveryExceptionHandlerWithState.handle(t1,
                        () -> dispatchEvent(de, eventHandler, result, Optional.of(eventDeliveryExceptionHandlerWithState)),
                        result::completeExceptionally,
                        () -> result.complete(null));
              }
              return null;
            }
    );
  }

  private Throwable unwrap(Throwable t1) {
    return t1 instanceof InvocationTargetException ? t1.getCause() : t1;
  }

}
