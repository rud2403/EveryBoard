package com.springbootvue.project.dto;

import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	private String email;
	private String password;
	private String name;
	private String role;
	
}
