package com.gdbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdbc.dao.UserDao;
import com.gdbc.domain.User;
import com.gdbc.service.UserService;

@Service  
public class UserServiceImpl implements UserService {

	@Autowired  
    private UserDao userDao;  
  
    public User selectUserById(Integer userId) {  
        return userDao.selectUserById(userId);  
          
    }  
}
