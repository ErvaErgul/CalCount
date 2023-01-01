package com.ervaergul.BackendBasics.Exceptions.CustomExceptions;

public class EntityAlreadyExistsException extends RuntimeException {

  public EntityAlreadyExistsException(String message) {
    super(message);
  }

}
