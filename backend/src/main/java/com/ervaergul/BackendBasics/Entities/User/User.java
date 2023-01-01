package com.ervaergul.BackendBasics.Entities.User;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ervaergul.BackendBasics.Entities.Daily.Daily;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /* 0 Male 1 Female */
  private Integer age;
  private String gender;
  private Integer height;
  private Integer weight;
  /* 1 through 5, 5 very active 1 not active */
  private Integer activityLevel;
  /* Lose Weight, Gain Weight, Maintain Weight */
  private String goal;

  private Double dailyCalorieGoal;
  private Double dailyProteinGoal;
  private Double dailyCarbohydrateGoal;
  private Double dailyFatGoal;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Daily> dailies;

  /* Basics */

  private String username;
  private String password;
  private boolean accountState;
  private String authority;

  private String refreshToken;
  private Date refreshTokenExpirationDate;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.accountState = true;
    this.authority = "user";
    this.refreshToken = UUID.randomUUID().toString();
    this.refreshTokenExpirationDate = Date.from(Instant.now().plusSeconds(31536000));
  }

}
