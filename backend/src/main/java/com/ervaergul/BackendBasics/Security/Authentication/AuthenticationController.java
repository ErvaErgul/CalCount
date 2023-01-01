package com.ervaergul.BackendBasics.Security.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.time.Duration;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.ervaergul.BackendBasics.Entities.User.User;
import com.ervaergul.BackendBasics.Entities.User.UserRepository;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.NotFoundException;
import com.ervaergul.BackendBasics.Exceptions.CustomExceptions.UnauthorizedException;
import com.ervaergul.BackendBasics.Security.Authentication.DTO.LoginRequest;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;

  @PutMapping("/refreshJwt/{date}")
  public ResponseEntity<Object> refreshJwt(HttpServletRequest request, @PathVariable String date) {
    Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
    String refreshToken = null;
    try {
      refreshToken = refreshTokenCookie.getValue();
    } catch (NullPointerException exception) {
      throw new NotFoundException("There is no refreshToken in the cookie");
    }

    User user = authenticationService.validateRefreshToken(refreshToken);

    return new ResponseEntity<>(authenticationService.refreshJwt(user, date), HttpStatus.OK);
  }

  @PostMapping("/login/{date}")
  public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest, @PathVariable String date,
      HttpServletResponse response) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
              loginRequest.getPassword()));
    } catch (AuthenticationException authenticationException) {
      throw new UnauthorizedException("Invalid username or password");
    }

    ResponseCookie refreshTokenCookie = ResponseCookie
        .from("refreshToken", authenticationService.generateRefreshToken(loginRequest.getUsername()))
        .httpOnly(true).maxAge(Duration.ofDays(365)).sameSite("Lax").path("/").build();

    response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

    return new ResponseEntity<>(
        authenticationService.refreshJwt(userRepository.findByIdOrUsernameIgnoreCase(null, loginRequest.getUsername()),
            date),
        HttpStatus.OK);
  }

  @DeleteMapping("/logout")
  public ResponseEntity<Object> logout(HttpServletResponse response) {
    ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "").httpOnly(true).maxAge(0)
        .sameSite("Lax").path("/").build();

    response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
