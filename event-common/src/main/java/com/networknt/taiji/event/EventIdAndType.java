package com.networknt.taiji.event;

/**
 * value object class for EntityId And Type
 *
 */
public class EventIdAndType {

  private EventId id;
  private String eventType;

  public EventIdAndType() {
  }

  public EventIdAndType(EventId id, String eventType) {
    this.id = id;
    this.eventType = eventType;
  }

  public EventId getId() {
    return id;
  }

  public void setId(EventId id) {
    this.id = id;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
}
