package com.salle.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salle.user.common.ApiResponse;
import com.salle.user.common.ApiUtil;
import com.salle.user.common.PagingRequest;
import com.salle.user.domain.User;
import com.salle.user.dto.UserDTO;
import com.salle.user.service.UserService;

import static com.salle.user.common.ApiResponse.emptyResponse;

import java.util.List;

import javax.validation.Valid;
@CrossOrigin("*")
@RestController
@RequestMapping("/user/api")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService usrSrv;
	
	@PostMapping("/user")
	public ResponseEntity<ApiResponse<User>> saveUser(@RequestBody @Valid UserDTO user) {
		ApiResponse<User> response = emptyResponse();
		response.setPayload(usrSrv.save(user));
		
		return new ResponseEntity<ApiResponse<User>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<ApiResponse<List<User>>> getAllUser(
			@RequestParam(required=false, defaultValue="false") Boolean usePaging,
			@RequestParam(required=false, defaultValue="0") Integer pageNo,
			@RequestParam(required=false, defaultValue="10") Integer sizePerPage,
			@RequestParam(required=false) List<String> orderByProperties
	){
		ApiResponse<List<User>> response = emptyResponse();
		PagingRequest req = ApiUtil.buildClientRequest(usePaging, pageNo, sizePerPage, orderByProperties);
		
		if(req.usePaging()) {
			Page<User> page = usrSrv.getAllUsersByPage(req);
			response.setPage(page);
			response.setPayload(page.getContent());
		} else {
			response.setPayload(usrSrv.getAllUsers());
		}
		
		return new ResponseEntity<ApiResponse<List<User>>>(response,HttpStatus.OK);
	}
}
