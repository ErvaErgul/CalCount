package com.ervaergul.BackendBasics.Entities.Food;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ervaergul.BackendBasics.Entities.Food.DTO.FoodCreationRequest;
import com.ervaergul.BackendBasics.Entities.Food.DTO.FoodResponse;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.NotFoundException;

@Service
public class FoodService {

  @Autowired
  private FoodRepository foodRepository;

  @Autowired
  private FoodTranslationRepository foodTranslationRepository;

  public FoodResponse convertFoodToFoodResponse(Food food, String language) {
    FoodResponse foodResponse = new FoodResponse();

    foodResponse.setFoodId(food.getId());

    foodResponse.setCaloriesPer100Grams(food.getCaloriesPer100Grams());
    foodResponse.setCaloriesPerGram(food.getCaloriesPer100Grams() / 100);

    foodResponse.setProteinPer100Grams(food.getProteinPer100Grams());
    foodResponse.setProteinPerGram(food.getProteinPer100Grams() / 100);

    foodResponse.setCarbohydratesPer100Grams(food.getCarbohydratesPer100Grams());
    foodResponse.setCarbohydratesPerGram(food.getCarbohydratesPer100Grams() / 100);

    foodResponse.setFatPer100Grams(food.getFatPer100Grams());
    foodResponse.setFatPerGram(food.getFatPer100Grams() / 100);

    foodResponse.setVerified(food.isVerified());

    if (language != null && !language.equals("english")) {
      FoodTranslation foodTranslation = foodTranslationRepository.findByFoodAndLanguageIgnoreCase(food, language);
      foodResponse.setName(foodTranslation.getNameTranslation());

      if (foodTranslation.getDescriptionTranslation() != null) {
        foodResponse.setDescription(foodTranslation.getDescriptionTranslation());
      }

    } else {
      foodResponse.setName(food.getEnglishName());

      if (food.getEnglishDescription() != null) {
        foodResponse.setDescription(food.getEnglishDescription());
      }
    }

    return foodResponse;
  }

  public List<FoodResponse> queryFoods(String queryParameter, String language) {
    List<FoodResponse> queryResults = new ArrayList<>();

    if (language != null && !language.equals("english")) {
      List<Food> foods = foodRepository
          .findByTranslationsNameTranslationContainsAndTranslationsLanguageIsIgnoreCase(queryParameter, language);
      if (foods == null || foods.size() == 0) {
        throw new NotFoundException("There are no foods that match the query parameter: " + queryParameter);
      }
      for (Food food : foods) {
        queryResults.add(convertFoodToFoodResponse(food, language));
      }
    } else {
      List<Food> foods = foodRepository
          .findByEnglishNameContainsIgnoreCase(queryParameter);
      if (foods == null || foods.size() == 0) {
        throw new NotFoundException("There are no foods that match the query parameter: " + queryParameter);
      }
      for (Food food : foods) {
        queryResults.add(convertFoodToFoodResponse(food, language));
      }
    }

    return queryResults;
  }

  public String createFood(FoodCreationRequest foodCreationRequest) {
    Food food = new Food();

    food.setEnglishName(foodCreationRequest.getEnglishName());

    if (foodCreationRequest.getEnglishDescription() != null) {
      food.setEnglishDescription(foodCreationRequest.getEnglishDescription());
    }

    food.setCaloriesPer100Grams(foodCreationRequest.getCaloriesPer100Grams());
    food.setProteinPer100Grams(foodCreationRequest.getProteinPer100Grams());
    food.setCarbohydratesPer100Grams(foodCreationRequest.getCarbohydratesPer100Grams());
    food.setFatPer100Grams(foodCreationRequest.getFatPer100Grams());
    food.setVerified(true);
    foodRepository.save(food);

    if (foodCreationRequest.getAdditionalLanguage() != null) {
      FoodTranslation foodTranslation = new FoodTranslation(foodCreationRequest.getAdditionalLanguage(),
          foodCreationRequest.getAdditionalLanguageNameTranslation(), food);

      if (foodCreationRequest.getAdditionalLanguageDescriptionTranslation() != null) {
        foodTranslation.setDescriptionTranslation(foodCreationRequest.getAdditionalLanguageDescriptionTranslation());
      }
      foodTranslation.setFood(food);
      foodTranslationRepository.save(foodTranslation);
    }

    return "Success";
  }

}
