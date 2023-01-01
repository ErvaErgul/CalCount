package com.ervaergul.BackendBasics.Entities.Food;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_translations")
@Data
@NoArgsConstructor
public class FoodTranslation {

  @Id
  private String id;

  private String language;
  private String nameTranslation;
  private String descriptionTranslation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id")
  private Food food;

  public FoodTranslation(String language, String nameTranslation, Food food) {
    this.id = food.getId() + "-" + language;
    this.language = language;
    this.nameTranslation = nameTranslation;
    this.food = food;
  }

}
