package com.blog.app.services;

import java.util.List;

import com.blog.app.payload.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUser(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer UserId);
	
}
