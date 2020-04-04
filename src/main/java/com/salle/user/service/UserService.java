package com.salle.user.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salle.user.common.AuthRequest;
import com.salle.user.common.AuthResponse;
import com.salle.user.common.PagingRequest;
import com.salle.user.common.UserSession;
import com.salle.user.domain.User;
import com.salle.user.dto.UserDTO;
import com.salle.user.exception.custom.DuplicateEmailException;
import com.salle.user.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public Long save(UserDTO userDTO) {
		if(userRepo.existsByEmail(userDTO.getEmail())) {
			throw new DuplicateEmailException("");
		}
		// 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		return userRepo.save(user).getId();
	}
	
	@Transactional(readOnly = true)
	public Page<User> getAllUsersByPage(PagingRequest req) {
		return userRepo.findAll(req.getPagingInfo().toPageable());
	}
	
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public AuthResponse<Void> authenticate(AuthRequest authReq, HttpServletRequest req) {
		Optional<User> optUser = userRepo.findByEmail(authReq.getEmail());
		AuthResponse<Void> authRes = new AuthResponse<>();
		if(optUser.isPresent()) {
			User user = optUser.get();
			boolean matched = passwordEncoder.matches(authReq.getPassword(), user.getPassword());
			if(matched) {
				// token 발행
				HttpSession session = req.getSession();
				UserSession userSession = transformIntoUserSessionFrom(user);
				session.setAttribute("loginInfo", userSession);
				authRes.setAuthToken(session.getId());

			} else {
				throw new RuntimeException("mismathced password");
			}
		} else {
			throw new RuntimeException("user not found");
		}
		
		return authRes;
	}
	
	public void invalidateAuthToken(HttpServletRequest req) {
		String authToken = req.getHeader("X-Auth-Token");
		if(authToken != null) {
			HttpSession session = req.getSession(false);
			if(session != null) {
				session.invalidate();
			} else {
				throw new RuntimeException(String.format("No session with %s", authToken));
			}
		}
	}
	
	private UserSession transformIntoUserSessionFrom(User user) {
		UserSession userSession = new UserSession();
		userSession.setEmail(user.getEmail());
		userSession.setUserRoleId(user.getUserRoleId());
		return userSession;
	}
}
