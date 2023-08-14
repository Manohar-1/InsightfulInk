package com.justreadit.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justreadit.blog.entities.User;
import com.justreadit.blog.exceptions.ResourceNotFoundException;
import com.justreadit.blog.payload.UserDto;
import com.justreadit.blog.repository.UserRepo;
import com.justreadit.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto); 
		User savedUser = userRepo.save(user); 
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto,Integer userId) {
		User savedUser = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
		
		savedUser.setId(userDto.getId());  
		savedUser.setName(userDto.getName()); 
		savedUser.setEmail(userDto.getEmail()); 
		savedUser.setPassword(userDto.getPassword()); 
		savedUser.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(savedUser); 
		UserDto userDtoUpdated = this.userToDto(updatedUser);  
		return userDtoUpdated;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		
		List<UserDto> userDtos = userList.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId)); 
		
		this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User u = new User(); 
		u.setId(userDto.getId());
		u.setName(userDto.getName()); 
		u.setEmail(userDto.getEmail()); 
		u.setPassword(userDto.getPassword());
		u.setAbout(userDto.getAbout());
		return u;
	}
	private UserDto userToDto(User user) {
		UserDto userDto = new UserDto(); 
		userDto.setId(user.getId());
		userDto.setName(user.getName()); 
		userDto.setEmail(user.getEmail()); 
		userDto.setPassword(user.getPassword()); 
		userDto.setAbout(user.getAbout());
		return userDto;
	}

}