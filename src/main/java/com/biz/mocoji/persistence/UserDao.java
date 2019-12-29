package com.biz.mocoji.persistence;

import org.apache.ibatis.annotations.Param;

import com.biz.mocoji.domain.UserDTO;

public interface UserDao {

	public int userInsert(UserDTO userDTO);

	public UserDTO findById(@Param("u_id") String u_id);
	
}
