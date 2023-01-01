package com.ervaergul.BackendBasics.Entities.Food.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodResponse {

  private Integer foodId;

  private String name;
  private String description;

  private Double caloriesPer100Grams;
  private Double caloriesPerGram;

  private Double proteinPer100Grams;
  private Double proteinPerGram;

  private Double carbohydratesPer100Grams;
  private Double carbohydratesPerGram;

  private Double fatPer100Grams;
  private Double fatPerGram;

  private boolean isVerified;

}
