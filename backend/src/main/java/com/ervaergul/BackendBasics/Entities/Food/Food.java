package com.ervaergul.BackendBasics.Entities.Food;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String englishName;
  private String englishDescription;

  private Double caloriesPer100Grams;
  private Double proteinPer100Grams;
  private Double carbohydratesPer100Grams;
  private Double fatPer100Grams;

  private boolean isVerified;

  @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
  private List<FoodTranslation> translations;

}
