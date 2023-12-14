package com.spring.javaProjectS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String userListGet() {
		
		return "study/user/userList";
	}
}
