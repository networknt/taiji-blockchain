package com.networknt.taiji.event;

public class OptimisticLockingException extends EventuateClientException {

  private EntityIdAndType entityIdAndType;
  private long entityVersion;

  public OptimisticLockingException(EntityIdAndType entityIdAndType, long entityVersion) {
    super(String.format("Couldn't update entity: %s, %s, %s", entityIdAndType.getEntityType(), entityIdAndType.getEntityId(), entityVersion));
    this.entityIdAndType = entityIdAndType;
    this.entityVersion = entityVersion;
  }

  public OptimisticLockingException() {
  }

  public EntityIdAndType getEntityIdAndType() {
    return entityIdAndType;
  }

  public long getEntityVersion() {
    return entityVersion;
  }
}

