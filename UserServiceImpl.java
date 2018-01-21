package com.hello.service.impl;

import com.hello.dao.UserDao;
import com.hello.dao.impl.UserDaoImpl;
import com.hello.entity.User;
import com.hello.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao=new UserDaoImpl();
	public void addUser(User user) throws Exception {
		userDao.addUser(user);
	}

}
