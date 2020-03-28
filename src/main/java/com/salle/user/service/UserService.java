package com.salle.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.salle.user.common.PagingRequest;
import com.salle.user.domain.User;
import com.salle.user.dto.UserDTO;
import com.salle.user.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	public User save(UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		return userRepo.save(user);
	}
	
	public Page<User> getAllUsersByPage(PagingRequest req) {
		return userRepo.findAll(req.getPagingInfo().toPageable());
	}
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
}
