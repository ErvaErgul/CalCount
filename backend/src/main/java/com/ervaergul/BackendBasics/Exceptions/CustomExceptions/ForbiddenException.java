package com.ervaergul.BackendBasics.Exceptions.CustomExceptions;

public class ForbiddenException extends RuntimeException {

  public ForbiddenException(String message) {
    super(message);
  }

}
