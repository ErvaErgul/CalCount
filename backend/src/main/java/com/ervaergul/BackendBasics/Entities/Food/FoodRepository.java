package com.ervaergul.BackendBasics.Entities.Food;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

  List<Food> findByEnglishNameContainsIgnoreCase(String queryParameter);

  List<Food> findByTranslationsNameTranslationContainsAndTranslationsLanguageIsIgnoreCase(String queryparameter,
      String language);

}
