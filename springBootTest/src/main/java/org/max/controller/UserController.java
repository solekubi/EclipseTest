package org.max.controller;

import org.max.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	 @RequestMapping("/{id}")  
	    public String view(@PathVariable("id") Long id,Model model) {  
	        User user = new User();  
	        user.setId(id);  
	        user.setName("zhang");
	        model.addAttribute(user);
	        return "index";  
	    }  
	 
	 
}
