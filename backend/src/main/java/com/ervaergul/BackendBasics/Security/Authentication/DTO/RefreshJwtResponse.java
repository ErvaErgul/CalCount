package com.ervaergul.BackendBasics.Security.Authentication.DTO;

import com.ervaergul.BackendBasics.Entities.Daily.DTO.DailyResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshJwtResponse {

  private Integer userId;
  private String username;
  private String authority;
  private DailyResponse dailyDetails;
  private String jwt;

}
