package com.ervaergul.BackendBasics.Entities.User;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ervaergul.BackendBasics.Entities.User.DTO.CredentialUpdateRequest;
import com.ervaergul.BackendBasics.Entities.User.DTO.RegistrationRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<Object> getUserDetails(@PathVariable Integer userId) {
    return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.OK);
  }

  @GetMapping("/{userId}/calculateDailyGoal")
  public ResponseEntity<Object> calculateDailyGoalsForUser(@PathVariable Integer userId) {
    return new ResponseEntity<>(userService.calculateDailyGoals(userId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
    return new ResponseEntity<>(userService.createUser(registrationRequest),
        HttpStatus.CREATED);
  }

  @PutMapping("/{shouldUpdateDailyGoals}")
  public ResponseEntity<Object> updateUserCredentials(
      @RequestBody @Valid CredentialUpdateRequest credentialUpdateRequest,
      @PathVariable String shouldUpdateDailyGoals) {
    return new ResponseEntity<>(
        userService.updateUserCredentials(credentialUpdateRequest, shouldUpdateDailyGoals),
        HttpStatus.OK);
  }

}
