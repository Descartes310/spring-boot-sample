package com.geloka.api.services;

import com.geloka.api.repositories.entities.User;

public interface UserService {

	User save(User user);
	
	User findByEmail(String email);
}
