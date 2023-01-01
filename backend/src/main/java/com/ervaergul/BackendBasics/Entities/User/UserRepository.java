package com.ervaergul.BackendBasics.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsernameIgnoreCase(String username);

  User findByRefreshToken(String refreshToken);

  User findByIdOrUsernameIgnoreCase(Integer id, String username);

}
