package com.ervaergul.BackendBasics.Entities.Daily;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ervaergul.BackendBasics.Entities.User.User;

@Repository
public interface DailyRepository extends JpaRepository<Daily, String> {

  Daily findByUserAndDate(User user, String date);

}
