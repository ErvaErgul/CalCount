package com.ervaergul.BackendBasics.Entities.User.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationRequest {

  @NotEmpty(message = "Username must be specified")
  private String username;
  @NotEmpty(message = "Password must be specified")
  private String password;

  private Integer age;
  private String gender;
  private Integer height;
  private Integer weight;
  private Integer activityLevel;
  private String goal;

}
