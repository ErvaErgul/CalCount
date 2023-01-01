package com.ervaergul.BackendBasics.Entities.Daily;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dailies")
public class DailyController {

  @Autowired
  private DailyService dailyService;

  @GetMapping("/{userId}/{date}")
  public ResponseEntity<Object> getDailyDetails(@PathVariable Integer userId, @PathVariable String date) {
    return new ResponseEntity<>(dailyService.getDailyDetails(userId, date), HttpStatus.OK);
  }

}
