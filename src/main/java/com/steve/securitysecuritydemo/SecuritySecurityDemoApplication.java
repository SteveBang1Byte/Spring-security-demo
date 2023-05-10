package com.steve.securitysecuritydemo;

import com.steve.securitysecuritydemo.entity.Role;
import com.steve.securitysecuritydemo.entity.User;
import com.steve.securitysecuritydemo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SecuritySecurityDemoApplication {
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SecuritySecurityDemoApplication.class, args);
	}

	@PostConstruct
	void initData(){
		String roleUser = "User";
		String roleManager = "Manager";
		String roleEditor = "Editor";
		String roleAdmin = "Admin";
		String roleSupperAdmin = "Supper_Admin";
		// Insert Role
		userService.createRole(new Role(roleUser));
		userService.createRole(new Role(roleManager));
		userService.createRole(new Role(roleEditor));
		userService.createRole(new Role(roleAdmin));
		userService.createRole(new Role(roleSupperAdmin));
		// Insert User
		userService.createUser(new User("endUser","123456"));
		userService.createUser(new User("manager","123456"));
		userService.createUser(new User("editor","123456"));
		userService.createUser(new User("supperAdmin","123456"));
		userService.createUser(new User("admin","123456"));
		//Insert role user
		userService.addRoleToUser("endUser",roleUser);
		userService.addRoleToUser("manager",roleManager);
		userService.addRoleToUser("editor",roleEditor);
		userService.addRoleToUser("editor",roleManager);
		userService.addRoleToUser("admin",roleAdmin);
		userService.addRoleToUser("supperAdmin",roleSupperAdmin);
		userService.addRoleToUser("supperAdmin",roleAdmin);
	}
}
