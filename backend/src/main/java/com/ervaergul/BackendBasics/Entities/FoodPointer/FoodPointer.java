package com.ervaergul.BackendBasics.Entities.FoodPointer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ervaergul.BackendBasics.Entities.Daily.Daily;
import com.ervaergul.BackendBasics.Entities.Food.Food;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_pointers")
@Data
@NoArgsConstructor
public class FoodPointer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer amountOfGrams;
  private String mealType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "daily_id")
  private Daily daily;

  public FoodPointer(Daily daily, Integer amountOfGrams, String mealType, Food food) {
    this.amountOfGrams = amountOfGrams;
    this.mealType = mealType;
    this.food = food;
    this.daily = daily;
  }

}
