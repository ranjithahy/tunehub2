package com.demo.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.demo.example.entities.Users;
import com.demo.example.services.UsersService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
//@RequestMapping("/users")
public class UsersController {
  @Autowired
  UsersService service;
	@PostMapping("/registration")
	public String addUsers(@ModelAttribute Users user)
	{
		boolean userStatus=service.emailExists(user.getEmail());
		if(userStatus == false)
	{
		service.addUser(user);
		System.out.println("user added");
	}
		else {
			System.out.println("user already exists");
		}
		return "home";

	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email,@RequestParam("password") String password,
			HttpSession session)
	{
		if(service.validateUser(email,password) == true)
		{
			String role=service.getRole(email);
			session.setAttribute("email", email);//creating session with variable name email and passing it into email
			 
			if(role.equals("admin"))
			 {
				 return "adminHome";
			 }
			 else {
				  return "customerHome";  
			 }
		}
		
		else {
			return "login";
		}
	}
	//@GetMapping("/getAllUsers")
	//public List<Users> findAll()
	//{
		//return service.findAll();
//	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "login";
	}
	
}
	

