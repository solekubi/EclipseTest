package org.max.controller;

import java.util.List;

import org.max.dao.UserDao;
import org.max.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	 @RequestMapping("/{id}")  
    public User view(@PathVariable("id") String id) {  
        List<User> users = userDao.findAll();
        return users.get(0);  
    }  
		 
}
