package com.jipvio.user.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jipvio.api.common.exception.JipvioException;
import com.jipvio.user.dto.AuthDTO;
import com.jipvio.user.dto.UserDTO;
import com.jipvio.user.entity.User;
import com.jipvio.user.repository.UserRepository;
import com.jipvio.api.common.exception.Error;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Transactional
	public Long save(UserDTO userDTO) {
		if(userRepo.existsByEmail(userDTO.getEmail())) {
			throw new JipvioException(Error.EmailAlreadyExists);
		}
		
		// 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       return userRepo.save(userDTO.toUserEntity(passwordEncoder.encode(userDTO.getPassword()))).getId();
	}
	
	public String authenticate(AuthDTO authReq, HttpServletRequest req) {
		Optional<User> optUser = userRepo.findByEmail(authReq.getEmail());
		String token = null;
		// 이메일이 존재하는지 확인한다.
		if(optUser.isPresent()) {
			User user = optUser.get();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			// 계정정보가 일치하는지 확인하고 토큰을 반환한다.
			if(passwordEncoder.matches(authReq.getPassword(), user.getPassword())) {
				HttpSession session = req.getSession();
				//session.setAttribute("loginInfo", value);
				token = session.getId();
			} else {
				throw new JipvioException(Error.InvalidPassword);
			}
		} else {
			throw new JipvioException(Error.UserNotFound);
		}
		
		return token;
	}
}
