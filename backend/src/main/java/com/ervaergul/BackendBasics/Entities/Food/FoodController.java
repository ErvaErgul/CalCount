package com.ervaergul.BackendBasics.Entities.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ervaergul.BackendBasics.Entities.Food.DTO.FoodCreationRequest;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

  @Autowired
  private FoodService foodService;

  @GetMapping(value = { "/query/{queryParameter}", "/query/{queryParameter}/language/{language}" })
  public ResponseEntity<Object> queryFoods(@PathVariable String queryParameter,
      @PathVariable(required = false) String language) {
    return new ResponseEntity<>(foodService.queryFoods(queryParameter, language), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> createFood(@RequestBody FoodCreationRequest foodCreationRequest) {
    return new ResponseEntity<>(foodService.createFood(foodCreationRequest), HttpStatus.OK);
  }

}
