package com.networknt.taiji.event;

import java.util.Map;
import java.util.Optional;

/**
 * An event with it's id which is the owner address plus hex number of max transactions for the
 * address + 1.
 */
public class EventWithMetadata {

  private Event event;
  private EventId id;
  private Optional<Map<String, String>> metadata;

  public EventWithMetadata(Event event, EventId id, Optional<Map<String, String>> metadata) {
    this.event = event;
    this.id = id;
    this.metadata = metadata;
  }

  public Event getEvent() {
    return event;
  }

  public EventId getId() {
    return id;
  }

  public Optional<Map<String, String>> getMetadata() {
    return metadata;
  }
}
