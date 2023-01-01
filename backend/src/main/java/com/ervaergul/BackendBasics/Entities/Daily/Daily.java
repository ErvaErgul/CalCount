package com.ervaergul.BackendBasics.Entities.Daily;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ervaergul.BackendBasics.Entities.FoodPointer.FoodPointer;
import com.ervaergul.BackendBasics.Entities.User.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dailies")
@Data
@NoArgsConstructor
public class Daily {

  @Id
  private String id;

  private String date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "daily", fetch = FetchType.LAZY)
  private List<FoodPointer> foods;

  public Daily(User user, String date) {
    this.id = user.getId() + " - " + date;
    this.date = date;
    this.user = user;
  }

}
