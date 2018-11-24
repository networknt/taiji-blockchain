package com.networknt.taiji.event;

import java.util.List;
import java.util.Optional;

public class EntityWithMetadata<T extends Aggregate> {

  private EntityIdAndVersion entityIdAndVersion;
  private long snapshotVersion;
  private List<EventWithMetadata> events;

  public EntityWithMetadata(EntityIdAndVersion entityIdAndVersion, long snapshotVersion, List<EventWithMetadata> events, T entity) {
    this.entityIdAndVersion = entityIdAndVersion;
    this.snapshotVersion = snapshotVersion;
    this.events = events;
    this.entity = entity;
  }

  private T entity;

  public T getEntity() {
    return entity;
  }

  public EntityIdAndVersion getEntityIdAndVersion() {
    return entityIdAndVersion;
  }

  public long getSnapshotVersion() {
    return snapshotVersion;
  }

  public EntityWithIdAndVersion<T> toEntityWithIdAndVersion() {
    return new EntityWithIdAndVersion<T>(entityIdAndVersion, entity);
  }

  public List<EventWithMetadata> getEvents() {
    return events;
  }
}
