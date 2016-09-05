package com.epam.rft.atsy.service.security;

import com.google.common.collect.Lists;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsAdapter implements UserDetails {

  private Long userId;
  private String password;
  private String username;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Lists.newArrayList();
  }

  public Long getUserId() {
    return userId;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
