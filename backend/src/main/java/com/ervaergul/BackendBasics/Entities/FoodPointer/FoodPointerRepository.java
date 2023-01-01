package com.ervaergul.BackendBasics.Entities.FoodPointer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ervaergul.BackendBasics.Entities.Daily.Daily;

@Repository
public interface FoodPointerRepository extends JpaRepository<FoodPointer, Integer> {

  public List<FoodPointer> findByDailyAndMealTypeIgnoreCase(Daily daily, String mealType);

}
