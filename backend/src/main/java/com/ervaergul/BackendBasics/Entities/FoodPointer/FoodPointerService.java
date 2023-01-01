package com.ervaergul.BackendBasics.Entities.FoodPointer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ervaergul.BackendBasics.Entities.Daily.Daily;
import com.ervaergul.BackendBasics.Entities.Daily.DailyService;
import com.ervaergul.BackendBasics.Entities.Food.Food;
import com.ervaergul.BackendBasics.Entities.Food.FoodRepository;
import com.ervaergul.BackendBasics.Entities.FoodPointer.DTO.FoodPointerResponse;
import com.ervaergul.BackendBasics.Entities.User.User;
import com.ervaergul.BackendBasics.Entities.User.UserService;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.NotFoundException;

@Service
public class FoodPointerService {

  @Autowired
  private FoodPointerRepository foodPointerRepository;

  @Autowired
  private FoodRepository foodRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private DailyService dailyService;

  public FoodPointerResponse convertFoodPointerToFoodPointerResponse(FoodPointer foodPointer, String mealType) {
    FoodPointerResponse foodPointerResponse = new FoodPointerResponse();

    foodPointerResponse.setFoodPointerId(foodPointer.getId());
    foodPointerResponse.setFoodId(foodPointer.getFood().getId());
    foodPointerResponse.setName(foodPointer.getFood().getEnglishName());
    foodPointerResponse.setAmountOfGrams(foodPointer.getAmountOfGrams());
    foodPointerResponse.setMealType(mealType);
    foodPointerResponse
        .setNumberOfCalories((foodPointer.getFood().getCaloriesPer100Grams() / 100) * foodPointer.getAmountOfGrams());
    foodPointerResponse.setGramsOfProtein(
        (foodPointer.getFood().getProteinPer100Grams() / 100) * foodPointer.getAmountOfGrams());
    foodPointerResponse.setGramsOfCarbohydrates(
        (foodPointer.getFood().getCarbohydratesPer100Grams() / 100) * foodPointer.getAmountOfGrams());
    foodPointerResponse
        .setGramsOfFat((foodPointer.getFood().getFatPer100Grams() / 100) * foodPointer.getAmountOfGrams());

    foodPointerResponse.setCaloriePerGram(foodPointer.getFood().getCaloriesPer100Grams() / 100);
    foodPointerResponse.setProteinPerGram(foodPointer.getFood().getProteinPer100Grams() / 100);
    foodPointerResponse.setCarbohydratesPerGram(foodPointer.getFood().getCarbohydratesPer100Grams() / 100);
    foodPointerResponse.setFatPerGram(foodPointer.getFood().getFatPer100Grams() / 100);

    return foodPointerResponse;
  }

  public String createAndAddNewFoodPointerToDaily(Integer userId, String date, Integer foodId, Integer amountOfGrams,
      String mealType) {
    User user = userService.getUserOrThrowException(userId);
    Daily daily = dailyService.getDailyOrInitializeDaily(user, date);
    Food food = foodRepository.findById(foodId).orElse(null);

    if (food == null) {
      throw new NotFoundException("There is no food with the id: " + foodId);
    }

    FoodPointer foodPointer = new FoodPointer(daily, amountOfGrams, mealType, food);
    foodPointerRepository.save(foodPointer);

    return "Success";
  }

  public String updateFoodPointerGrams(Integer foodPointerId, Integer amountOfGrams) {
    FoodPointer foodPointer = foodPointerRepository.findById(foodPointerId).orElse(null); // Needs exception handling

    if (foodPointer == null) {
      throw new NotFoundException("There is no foodPointer with the id:" + foodPointerId);
    }

    foodPointer.setAmountOfGrams(amountOfGrams);
    foodPointerRepository.save(foodPointer);

    return "Success";
  }

  public String deleteFoodPointer(Integer foodPointerId) {
    FoodPointer foodPointer = foodPointerRepository.findById(foodPointerId).orElse(null); // Needs exception handling

    foodPointerRepository.delete(foodPointer);

    return "Success";
  }

}
