package com.ervaergul.BackendBasics.Entities.FoodPointer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foodpointers")
public class FoodPointerController {

  @Autowired
  private FoodPointerService foodPointerService;

  @PostMapping("/{userId}/{date}/{foodId}/{amountOfGrams}/{mealType}")
  public ResponseEntity<Object> addFoodPointerToDaily(@PathVariable Integer userId, @PathVariable String date,
      @PathVariable Integer foodId, @PathVariable Integer amountOfGrams, @PathVariable String mealType) {
    return new ResponseEntity<>(
        foodPointerService.createAndAddNewFoodPointerToDaily(userId, date, foodId, amountOfGrams, mealType),
        HttpStatus.OK);
  }

  @PutMapping("/{foodPointerId}/{amountOfGrams}")
  public ResponseEntity<Object> updateFoodPointerGrams(@PathVariable Integer foodPointerId,
      @PathVariable Integer amountOfGrams) {
    return new ResponseEntity<>(
        foodPointerService.updateFoodPointerGrams(foodPointerId, amountOfGrams), HttpStatus.OK);
  }

  @DeleteMapping("/{foodPointerId}")
  public ResponseEntity<Object> deleteFoodPointer(@PathVariable Integer foodPointerId) {
    return new ResponseEntity<>(
        foodPointerService.deleteFoodPointer(foodPointerId), HttpStatus.OK);
  }

}
