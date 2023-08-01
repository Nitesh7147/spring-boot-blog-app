package com.blog.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entities.User;
import com.blog.app.payload.UserDto;
import com.blog.app.repositories.UserRepository;
import com.blog.app.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// when we will call save() so we have to pass user(User entity object) but we have object of UserDto
		// so we have to convert UserDto to User - for this conversion we can use model mapper library or by method also
		// model mapper -> ek object ko dusri class ke object me convert karne me
		
	    User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server: " + userId));
			user.setEmail(userDto.getEmail());
			user.setAbout(userDto.getAbout());
			User updatedUser = this.userRepository.save(user);
			return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server: " + userId));
		UserDto userDto = this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList =	this.userRepository.findAll();
		List<UserDto> dtoList = userList.stream().map( listUserDto -> this.userToDto(listUserDto)).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server: " + userId));
		this.userRepository.delete(user);
	}
	
	
	/**
	 * Method to convert UserDto to User
	 */
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	/**
	 * Method to convert User to UserDto
	 */
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}




// By manually setting
///**
// * Method to convert UserDto to User
// */
//private User dtoToUser(UserDto userDto) {
//	User user = new User();
//	user.setUserId(userDto.getUserId());
//	user.setName(userDto.getName());
//	user.setEmail(userDto.getEmail());
//	user.setPassword(userDto.getPassword());
//	user.setAbout(userDto.getAbout());
//	return user;
//}
//
///**
// * Method to convert User to UserDto
// */
//private UserDto userToDto(User user) {
//	UserDto userDto = new UserDto();
//	userDto.setUserId(user.getUserId());
//	userDto.setName(user.getName());
//	userDto.setEmail(user.getEmail());
//	userDto.setPassword(user.getPassword());
//	userDto.setAbout(user.getAbout());
//	return userDto;
//}
