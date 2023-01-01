package com.ervaergul.BackendBasics.Entities.User.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialUpdateRequest {

  @NotNull(message = "User ID must be specified")
  private Integer userId;

  private Integer age;
  private String gender;
  private Integer height;
  private Integer weight;
  private Integer activityLevel;
  private String goal;
  private Double dailyCalorieGoal;
  private Double dailyProteinGoal;
  private Double dailyCarbohydrateGoal;
  private Double dailyFatGoal;

}
