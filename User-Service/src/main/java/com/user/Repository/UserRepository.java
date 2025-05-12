package com.user.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.UserModel.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
		Optional<User> findByUsername(String username);
}
