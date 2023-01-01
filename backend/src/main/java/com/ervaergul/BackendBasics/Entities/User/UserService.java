package com.ervaergul.BackendBasics.Entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ervaergul.BackendBasics.Entities.User.DTO.CredentialUpdateRequest;
import com.ervaergul.BackendBasics.Entities.User.DTO.RegistrationRequest;
import com.ervaergul.BackendBasics.Entities.User.DTO.UserResponse;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.EntityAlreadyExistsException;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.NotFoundException;

@Service
public class UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  public UserResponse convertUserToUserResponse(User user) {
    UserResponse userResponse = new UserResponse();

    userResponse.setUserId(user.getId());
    userResponse.setUsername(user.getUsername());
    userResponse.setAuthority(user.getAuthority());
    userResponse.setAge(user.getAge());
    userResponse.setGender(user.getGender());
    userResponse.setHeight(user.getHeight());
    userResponse.setWeight(user.getWeight());
    userResponse.setActivityLevel(user.getActivityLevel());
    userResponse.setGoal(user.getGoal());
    userResponse.setDailyCalorieGoal(user.getDailyCalorieGoal());
    userResponse.setDailyProteinGoal(user.getDailyProteinGoal());
    userResponse.setDailyCarbohydrateGoal(user.getDailyCarbohydrateGoal());
    userResponse.setDailyFatGoal(user.getDailyFatGoal());

    return userResponse;
  }

  public User getUserOrThrowException(Integer userId) {
    User user = userRepository.findById(userId).orElse(null);

    if (user == null) {
      throw new NotFoundException("There is no user with the id: " + userId);
    }

    return user;
  }

  public UserResponse calculateDailyGoals(Integer userId) {
    User user = getUserOrThrowException(userId);

    Double dailyCalorieGoal = null;
    Double BMR = null;

    if (user.getGender().equals("0")) {
      BMR = 66.47 + (13.75 * user.getWeight()) + (5.003 * user.getHeight()) - (6.755 * user.getAge());
    } else {
      BMR = 655.1 + (9.563 * user.getWeight()) + (1.850 * user.getHeight()) - (4.676 * user.getAge());
    }

    if (user.getActivityLevel() == 1) {
      BMR = BMR * 1.2;
    }
    if (user.getActivityLevel() == 2) {
      BMR = BMR * 1.375;
    }
    if (user.getActivityLevel() == 3) {
      BMR = BMR * 1.55;
    }
    if (user.getActivityLevel() == 4) {
      BMR = BMR * 1.725;
    }
    if (user.getActivityLevel() == 5) {
      BMR = BMR * 1.9;
    }

    if (user.getGoal().equals("loseWeight")) {
      dailyCalorieGoal = BMR - 750;
    }

    if (user.getGoal().equals("gainWeight")) {
      dailyCalorieGoal = BMR + 250;
    }

    if (user.getGoal().equals("maintainWeight")) {
      dailyCalorieGoal = BMR;
    }

    user.setDailyCalorieGoal(dailyCalorieGoal);

    Double proteinGoal = ((dailyCalorieGoal * 25) / 100) / 4;
    Double carbohydratesGoal = ((dailyCalorieGoal * 50) / 100) / 4;
    Double fatGoal = ((dailyCalorieGoal * 25) / 100) / 9;

    user.setDailyProteinGoal(proteinGoal);
    user.setDailyCarbohydrateGoal(carbohydratesGoal);
    user.setDailyFatGoal(fatGoal);

    userRepository.save(user);

    return convertUserToUserResponse(user);
  }

  public UserResponse getUserDetails(Integer userId) {
    return convertUserToUserResponse(getUserOrThrowException(userId));
  }

  public UserResponse createUser(RegistrationRequest registrationRequest) {
    if (userRepository.findByUsernameIgnoreCase(registrationRequest.getUsername()) != null) {
      throw new EntityAlreadyExistsException("Username is taken.");
    }

    User user = new User(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()));

    user.setAge(registrationRequest.getAge());
    user.setGender(registrationRequest.getGender());
    user.setHeight(registrationRequest.getHeight());
    user.setWeight(registrationRequest.getWeight());
    user.setActivityLevel(registrationRequest.getActivityLevel());
    user.setGoal(registrationRequest.getGoal());

    userRepository.save(user);

    return calculateDailyGoals(user.getId());
  }

  public String updateUserCredentials(CredentialUpdateRequest credentialUpdateRequest,
      String shouldCalculateDailyGoals) {
    User user = getUserOrThrowException(credentialUpdateRequest.getUserId());

    if (credentialUpdateRequest.getAge() != null) {
      user.setAge(credentialUpdateRequest.getAge());
    }

    if (credentialUpdateRequest.getGender() != null) {
      user.setGender(credentialUpdateRequest.getGender());
    }

    if (credentialUpdateRequest.getHeight() != null) {
      user.setHeight(credentialUpdateRequest.getHeight());
    }

    if (credentialUpdateRequest.getWeight() != null) {
      user.setWeight(credentialUpdateRequest.getWeight());
    }

    if (credentialUpdateRequest.getActivityLevel() != null) {
      user.setActivityLevel(credentialUpdateRequest.getActivityLevel());
    }

    if (credentialUpdateRequest.getGoal() != null) {
      user.setGoal(credentialUpdateRequest.getGoal());
    }

    if (credentialUpdateRequest.getDailyCalorieGoal() != null) {
      user.setDailyCalorieGoal(credentialUpdateRequest.getDailyCalorieGoal());
    }

    if (credentialUpdateRequest.getDailyProteinGoal() != null) {
      user.setDailyProteinGoal(credentialUpdateRequest.getDailyProteinGoal());
    }

    if (credentialUpdateRequest.getDailyCarbohydrateGoal() != null) {
      user.setDailyCarbohydrateGoal(credentialUpdateRequest.getDailyCarbohydrateGoal());
    }

    if (credentialUpdateRequest.getDailyFatGoal() != null) {
      user.setDailyFatGoal(credentialUpdateRequest.getDailyFatGoal());
    }

    userRepository.save(user);

    if (shouldCalculateDailyGoals.equals("yes")) {
      calculateDailyGoals(user.getId());
    }

    return "Success";
  }

}
