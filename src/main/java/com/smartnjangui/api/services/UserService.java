package com.smartnjangui.api.services;

import com.smartnjangui.api.repositories.entities.User;

public interface UserService {

	User save(User user);
	
	User findByEmail(String email);
}
