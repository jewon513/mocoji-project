package com.biz.mocoji.service;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.mocoji.domain.UserDTO;
import com.biz.mocoji.persistence.UserDao;

@Service
public class UserService {

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	BCryptPasswordEncoder passwordENcoder;
	
	UserDao uDao;
	
	@Autowired
	public void newUserDao() {
		uDao = sqlSession.getMapper(UserDao.class);
	}
	
	
	public int userJoin(@Valid UserDTO userDTO) {

		String cryptext = passwordENcoder.encode(userDTO.getU_password());
		userDTO.setU_password(cryptext);
		
		return uDao.userInsert(userDTO);
		
	}

	public boolean userIdCheck(String u_id) {

		UserDTO userDTO = uDao.findById(u_id);
		
		if(userDTO != null && userDTO.getU_id().equalsIgnoreCase(u_id)) {
			return true;
		}
		return false;
	}
	
	public boolean userLoginCheck(UserDTO userDTO) {

		String inU_id = userDTO.getU_id();
		String inU_pass = userDTO.getU_password();
		
		UserDTO userRDTO = uDao.findById(inU_id);
		
		if(userRDTO == null) {
			return false;
		}
		
		String sU_id = userRDTO.getU_id();
		String sU_pass = userRDTO.getU_password();
		
		String cryptU_pass = userRDTO.getU_password();
		
		if(sU_id.equalsIgnoreCase(inU_id) && passwordENcoder.matches(inU_pass, cryptU_pass)) {
			
			return true;
		}else {
			return false;	
		}
		
	}

	public UserDTO getUser(String u_id) {

		return uDao.findById(u_id);
	}



}
