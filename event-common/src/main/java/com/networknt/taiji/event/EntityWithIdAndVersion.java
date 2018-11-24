package com.networknt.taiji.event;

public class EntityWithIdAndVersion<T> {
  private final EntityIdAndVersion entityIdAndVersion;
  private final T aggregate;

  @Override
  public String toString() {
    return "EntityWithIdAndVersion{" +
            "entityIdAndVersion=" + entityIdAndVersion +
            ", aggregate=" + aggregate +
            '}';
  }

  public EntityWithIdAndVersion(EntityIdAndVersion entityIdAndVersion, T aggregate) {

    this.entityIdAndVersion = entityIdAndVersion;
    this.aggregate = aggregate;
  }

  public EntityIdAndVersion getEntityIdAndVersion() {
    return entityIdAndVersion;
  }

  public T getAggregate() {
    return aggregate;
  }

  public String getEntityId() {
    return entityIdAndVersion.getEntityId();
  }

  public long getEntityVersion() {
    return entityIdAndVersion.getEntityVersion();
  }
}
