package com.ervaergul.BackendBasics.Entities.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTranslationRepository extends JpaRepository<FoodTranslation, String> {

  FoodTranslation findByFoodAndLanguageIgnoreCase(Food food, String language);

}
