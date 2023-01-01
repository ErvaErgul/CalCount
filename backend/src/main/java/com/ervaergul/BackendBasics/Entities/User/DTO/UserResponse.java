package com.ervaergul.BackendBasics.Entities.User.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

  private Integer userId;
  private String username;
  private String authority;
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
