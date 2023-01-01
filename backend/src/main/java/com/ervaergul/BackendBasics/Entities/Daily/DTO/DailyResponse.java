package com.ervaergul.BackendBasics.Entities.Daily.DTO;

import java.util.List;

import com.ervaergul.BackendBasics.Entities.FoodPointer.DTO.FoodPointerResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyResponse {

  private String dailyId;

  private Double numberOfCaloriesToConsume;
  private Double numberOfCaloriesConsumed;
  private Double numberOfCaloriesLeft;

  private Double gramsOfProteinToConsume;
  private Double gramsOfProteinConsumed;

  private Double gramsOfCarbohydratesToConsume;
  private Double gramsOfCarbohydratesConsumed;

  private Double gramsOfFatToConsume;
  private Double gramsOfFatConsumed;

  private List<FoodPointerResponse> breakfastFoods;
  private List<FoodPointerResponse> lunchFoods;
  private List<FoodPointerResponse> dinnerFoods;
  private List<FoodPointerResponse> snackFoods;

  private String status;

}
