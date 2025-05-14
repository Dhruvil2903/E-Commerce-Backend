package com.user.Service;

import java.util.Optional;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.CustomException.NoUserFoundException;
import com.user.CustomException.UserAlreadyExistException;
import com.user.Repository.UserRepository;
import com.user.UserEnum.Role;
import com.user.UserModel.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Optional<User> login(User user) throws NoUserFoundException, InvalidCredentialsException {
		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

		if (!existingUser.isPresent()) {
			throw new NoUserFoundException("User does not exist");
		}
		User user2 = existingUser.get();
		if (!passwordEncoder.matches(user.getPassword(), user2.getPassword())) {
			throw new InvalidCredentialsException("Invalid credintials");
		}
		
		System.out.println("Login successfull");
		return existingUser;

	}

	public User register(User user) throws UserAlreadyExistException {
//		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new UserAlreadyExistException("User is already created");
		}
		System.out.println("User is registered successfully");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRole(Role.USER);
		
		return userRepository.save(user);
	}
}
