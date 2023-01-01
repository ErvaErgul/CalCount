package com.ervaergul.BackendBasics.Entities.Daily;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ervaergul.BackendBasics.Entities.Daily.DTO.DailyResponse;
import com.ervaergul.BackendBasics.Entities.FoodPointer.FoodPointer;
import com.ervaergul.BackendBasics.Entities.FoodPointer.FoodPointerRepository;
import com.ervaergul.BackendBasics.Entities.FoodPointer.FoodPointerService;
import com.ervaergul.BackendBasics.Entities.FoodPointer.DTO.FoodPointerResponse;
import com.ervaergul.BackendBasics.Entities.User.User;
import com.ervaergul.BackendBasics.Entities.User.UserService;

@Service
public class DailyService {

  @Autowired
  private DailyRepository dailyRepository;

  @Autowired
  private FoodPointerRepository foodPointerRepository;

  @Autowired
  private FoodPointerService foodPointerService;

  @Autowired
  private UserService userService;

  public DailyResponse convertDailyToDailyResponse(Daily daily) {
    DailyResponse dailyResponse = new DailyResponse();

    dailyResponse.setDailyId(daily.getId());
    dailyResponse.setNumberOfCaloriesToConsume(daily.getUser().getDailyCalorieGoal());

    Double numberOfCaloriesConsumed = 0.0;
    Double gramsOfProteinConsumed = 0.0;
    Double gramsOfCarbohydratesConsumed = 0.0;
    Double gramsOfFatConsumed = 0.0;
    Double numberOfCaloriesLeft = daily.getUser().getDailyCalorieGoal();

    List<FoodPointerResponse> breakfastFoods = new ArrayList<>();

    for (FoodPointer foodPointer : foodPointerRepository.findByDailyAndMealTypeIgnoreCase(daily, "breakfast")) {
      breakfastFoods.add(foodPointerService.convertFoodPointerToFoodPointerResponse(foodPointer, "breakfast"));
    }

    for (FoodPointerResponse foodPointerResponse : breakfastFoods) {
      numberOfCaloriesConsumed += foodPointerResponse.getNumberOfCalories().intValue();
      gramsOfProteinConsumed += foodPointerResponse.getGramsOfProtein().intValue();
      gramsOfCarbohydratesConsumed += foodPointerResponse.getGramsOfCarbohydrates().intValue();
      gramsOfFatConsumed += foodPointerResponse.getGramsOfFat().intValue();
    }

    dailyResponse.setBreakfastFoods(breakfastFoods);

    List<FoodPointerResponse> lunchFoods = new ArrayList<>();

    for (FoodPointer foodPointer : foodPointerRepository.findByDailyAndMealTypeIgnoreCase(daily, "lunch")) {
      lunchFoods.add(foodPointerService.convertFoodPointerToFoodPointerResponse(foodPointer, "lunch"));
    }

    for (FoodPointerResponse foodPointerResponse : lunchFoods) {
      numberOfCaloriesConsumed += foodPointerResponse.getNumberOfCalories().intValue();
      gramsOfProteinConsumed += foodPointerResponse.getGramsOfProtein().intValue();
      gramsOfCarbohydratesConsumed += foodPointerResponse.getGramsOfCarbohydrates().intValue();
      gramsOfFatConsumed += foodPointerResponse.getGramsOfFat().intValue();
    }

    dailyResponse.setLunchFoods(lunchFoods);

    List<FoodPointerResponse> dinnerFoods = new ArrayList<>();

    for (FoodPointer foodPointer : foodPointerRepository.findByDailyAndMealTypeIgnoreCase(daily, "dinner")) {
      dinnerFoods.add(foodPointerService.convertFoodPointerToFoodPointerResponse(foodPointer, "dinner"));
    }

    for (FoodPointerResponse foodPointerResponse : dinnerFoods) {
      numberOfCaloriesConsumed += foodPointerResponse.getNumberOfCalories().intValue();
      gramsOfProteinConsumed += foodPointerResponse.getGramsOfProtein().intValue();
      gramsOfCarbohydratesConsumed += foodPointerResponse.getGramsOfCarbohydrates().intValue();
      gramsOfFatConsumed += foodPointerResponse.getGramsOfFat().intValue();
    }

    dailyResponse.setDinnerFoods(dinnerFoods);

    List<FoodPointerResponse> snackFoods = new ArrayList<>();

    for (FoodPointer foodPointer : foodPointerRepository.findByDailyAndMealTypeIgnoreCase(daily, "snack")) {
      snackFoods.add(foodPointerService.convertFoodPointerToFoodPointerResponse(foodPointer, "snack"));
    }

    for (FoodPointerResponse foodPointerResponse : snackFoods) {
      numberOfCaloriesConsumed += foodPointerResponse.getNumberOfCalories().intValue();
      gramsOfProteinConsumed += foodPointerResponse.getGramsOfProtein().intValue();
      gramsOfCarbohydratesConsumed += foodPointerResponse.getGramsOfCarbohydrates().intValue();
      gramsOfFatConsumed += foodPointerResponse.getGramsOfFat().intValue();
    }

    dailyResponse.setSnackFoods(snackFoods);

    dailyResponse.setNumberOfCaloriesConsumed(numberOfCaloriesConsumed);

    dailyResponse.setGramsOfProteinToConsume(daily.getUser().getDailyProteinGoal());
    dailyResponse.setGramsOfProteinConsumed(gramsOfProteinConsumed);

    dailyResponse.setGramsOfCarbohydratesToConsume(daily.getUser().getDailyCarbohydrateGoal());
    dailyResponse.setGramsOfCarbohydratesConsumed(gramsOfCarbohydratesConsumed);

    dailyResponse.setGramsOfFatToConsume(daily.getUser().getDailyFatGoal());
    dailyResponse.setGramsOfFatConsumed(gramsOfFatConsumed);

    numberOfCaloriesLeft = daily.getUser().getDailyCalorieGoal() - numberOfCaloriesConsumed;
    dailyResponse.setNumberOfCaloriesLeft(numberOfCaloriesLeft);

    if (numberOfCaloriesLeft > 0) {
      dailyResponse.setStatus("You have " + numberOfCaloriesLeft + " calories left to consume.");
    } else {
      dailyResponse
          .setStatus("You have consumed " + numberOfCaloriesLeft + " extra calories than you are supposed to.");
    }

    return dailyResponse;
  }

  public Daily getDailyOrInitializeDaily(User user, String date) {
    Daily daily = dailyRepository.findByUserAndDate(user, date);

    if (daily == null) {
      daily = new Daily(user, date);
      dailyRepository.save(daily);
    }

    return daily;
  }

  public DailyResponse getDailyDetails(Integer userId, String date) {
    User user = userService.getUserOrThrowException(userId);
    Daily daily = getDailyOrInitializeDaily(user, date);

    return convertDailyToDailyResponse(daily);
  }

}
