package com.ervaergul.BackendBasics.Exceptions.CustomExceptions;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }

}
