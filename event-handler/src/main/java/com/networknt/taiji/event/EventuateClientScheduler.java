package com.networknt.taiji.event;

/**
 * Abstraction of a scheduler
 */
public interface EventuateClientScheduler {

  /**
   * Asynchronously invoke the callback after a delay
   * @param delayInMilliseconds the delay
   * @param callback the callback
   */
  void setTimer(long delayInMilliseconds, Runnable callback);
}
