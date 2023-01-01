package com.ervaergul.BackendBasics.Security.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ervaergul.BackendBasics.Entities.User.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(String username) {
    UserDetailsImpl userDetailsImpl = new UserDetailsImpl(userRepository.findByUsernameIgnoreCase(username));

    return userDetailsImpl;
  }

}
