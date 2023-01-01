package com.ervaergul.BackendBasics.Security.Authentication.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

  @NotEmpty(message = "Username must be specified")
  private String username;
  @NotEmpty(message = "Password must be specified")
  private String password;

}
