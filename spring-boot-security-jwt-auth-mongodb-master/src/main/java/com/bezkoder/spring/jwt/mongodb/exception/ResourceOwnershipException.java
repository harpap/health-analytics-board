package com.bezkoder.spring.jwt.mongodb.exception;

public class ResourceOwnershipException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ResourceOwnershipException(String msg) {
    super(msg);
  }
}
