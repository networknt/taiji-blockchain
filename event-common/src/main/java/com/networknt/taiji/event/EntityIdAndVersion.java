package com.networknt.taiji.event;

import java.util.Objects;

/**
 * value object class for EntityId And Version
 *
 */
public class EntityIdAndVersion {

  private final String entityId;
  private final long entityVersion;

  @Override
  public String toString() {
    return "EntityIdAndVersion{" +
            "entityId='" + entityId + '\'' +
            ", entityVersion=" + entityVersion +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EntityIdAndVersion that = (EntityIdAndVersion) o;
    return entityVersion == that.entityVersion &&
            Objects.equals(entityId, that.entityId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entityId, entityVersion);
  }

  public EntityIdAndVersion(String entityId, long entityVersion) {
    this.entityId = entityId;
    this.entityVersion = entityVersion;
  }

  public String getEntityId() {
    return entityId;
  }

  public long getEntityVersion() {
    return entityVersion;
  }
}
