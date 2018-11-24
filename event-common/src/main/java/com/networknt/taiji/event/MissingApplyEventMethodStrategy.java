package com.networknt.taiji.event;

public interface MissingApplyEventMethodStrategy {

  boolean supports(Aggregate aggregate, MissingApplyMethodException e);
  void handle(Aggregate aggregate, MissingApplyMethodException e);

}
