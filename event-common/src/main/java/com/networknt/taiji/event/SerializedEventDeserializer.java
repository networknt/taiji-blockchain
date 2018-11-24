package com.networknt.taiji.event;

import java.util.Optional;

public interface SerializedEventDeserializer {
  Optional<DispatchedEvent<Event>> toDispatchedEvent(SerializedEvent se);
}
