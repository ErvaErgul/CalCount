package com.ervaergul.BackendBasics.Exceptions.CustomExceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

}
