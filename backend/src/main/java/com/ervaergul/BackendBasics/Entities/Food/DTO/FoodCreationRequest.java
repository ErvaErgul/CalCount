package com.ervaergul.BackendBasics.Entities.Food.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodCreationRequest {

  private String brandName;

  private String englishName;
  private String englishDescription;

  private Double caloriesPer100Grams;
  private Double proteinPer100Grams;
  private Double carbohydratesPer100Grams;
  private Double fatPer100Grams;

  private String additionalLanguage;
  private String additionalLanguageNameTranslation;
  private String additionalLanguageDescriptionTranslation;

}
