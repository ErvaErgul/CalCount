package com.ervaergul.BackendBasics.Security.Authentication;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ervaergul.BackendBasics.Entities.Daily.Daily;
import com.ervaergul.BackendBasics.Entities.Daily.DailyService;
import com.ervaergul.BackendBasics.Entities.User.User;
import com.ervaergul.BackendBasics.Entities.User.UserRepository;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.UnauthorizedException;
import com.ervaergul.BackendBasics.Security.Authentication.DTO.RefreshJwtResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DailyService dailyService;

  public String generateRefreshToken(String username) {
    User user = userRepository.findByUsernameIgnoreCase(username);
    user.setRefreshToken(UUID.randomUUID().toString());
    user.setRefreshTokenExpirationDate(Date.from(Instant.now().plusSeconds(31536000)));
    userRepository.save(user);
    return user.getRefreshToken();
  }

  public User validateRefreshToken(String refreshToken) {
    User user = userRepository.findByRefreshToken(refreshToken);

    if (user != null && !user.getRefreshTokenExpirationDate().before(new Date())) {
      return user;
    }

    throw new UnauthorizedException("Invalid refresh token");
  }

  public String generateJwt(String username) {
    User user = userRepository.findByUsernameIgnoreCase(username);
    return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plusSeconds(86400)))
        .signWith(SignatureAlgorithm.HS256, "jwtGenerationSecret").compact();
  }

  public User validateJwt(String jwt) {
    Claims jwtClaims = Jwts.parser().setSigningKey("jwtGenerationSecret").parseClaimsJws(jwt).getBody();
    String jwtSubject = jwtClaims.getSubject();
    Date jwtExpiration = jwtClaims.getExpiration();
    User user = userRepository.findByUsernameIgnoreCase(jwtSubject);

    if (user != null && !jwtExpiration.before(new Date())) {
      return user;
    }

    throw new UnauthorizedException("Invalid JWT");
  }

  public RefreshJwtResponse refreshJwt(User user, String date) {
    RefreshJwtResponse refreshJwtResponse = new RefreshJwtResponse();

    Daily daily = dailyService.getDailyOrInitializeDaily(user, date);

    refreshJwtResponse.setUserId(daily.getUser().getId());
    refreshJwtResponse.setUsername(daily.getUser().getUsername());
    refreshJwtResponse.setDailyDetails(dailyService.convertDailyToDailyResponse(daily));
    refreshJwtResponse.setJwt(generateJwt(user.getUsername()));

    return refreshJwtResponse;
  }

}
