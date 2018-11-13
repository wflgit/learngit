package com.gdbc.dao;

import com.gdbc.domain.User;


public interface UserDao {
	
	/**
	 * @param userId
	 * @return User
	 */
	public User selectUserById(Integer userId);  

}
