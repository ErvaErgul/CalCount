package com.ervaergul.BackendBasics.Security.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ervaergul.BackendBasics.Entities.User.User;

public record UserDetailsImpl(User user) implements UserDetails {

  @Override
  public String getUsername() {
    return this.user.getUsername();
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(new SimpleGrantedAuthority(this.user.getAuthority()));
    return authorityList;
  }

  @Override
  public boolean isEnabled() {
    return this.user.isAccountState();
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.user.isAccountState();
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.user.isAccountState();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.user.isAccountState();
  }

}
