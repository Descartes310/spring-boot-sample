package com.smartnjangui.api.services.implementations;

import org.springframework.stereotype.Service;

import com.smartnjangui.api.repositories.UserRepository;
import com.smartnjangui.api.repositories.entities.User;
import com.smartnjangui.api.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		// TODO Auto-generated constructor stub
		this.userRepository = userRepository;
	}
	
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return this.userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return this.userRepository.findByEmail(email);
	}

}
