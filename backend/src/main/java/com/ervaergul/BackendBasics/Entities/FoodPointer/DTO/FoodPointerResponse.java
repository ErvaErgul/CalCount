package com.ervaergul.BackendBasics.Entities.FoodPointer.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodPointerResponse {

  private Integer foodPointerId;
  private Integer foodId;

  private String name;
  private Integer amountOfGrams;
  private String mealType;

  private Double numberOfCalories;
  private Double gramsOfProtein;
  private Double gramsOfCarbohydrates;
  private Double gramsOfFat;

  private Double caloriePerGram;
  private Double proteinPerGram;
  private Double carbohydratesPerGram;
  private Double fatPerGram;

}
