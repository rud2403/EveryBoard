package com.springbootvue.project.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootvue.project.dto.UserDTO;
import com.springbootvue.project.service.SecurityService;
import com.springbootvue.project.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 회원가입
	@RequestMapping(value = "/api/signUp", method = RequestMethod.POST)
	public int insert(@RequestBody UserDTO userDTO) {
		
		int result = 0;
		
		try {
			
			userService.signUp(userDTO);
			
			result = 1;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			result = 0;
		}
		return result;
	}
	
	// 로그인
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestBody UserDTO userDTO) throws Exception {
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		// 이메일에 맞는 패스워드를 들고온다.
		Map<String, Object> result = userService.login(userDTO);
		
		String email = userDTO.getEmail();
		String password = (String)result.get("password");
		
		// 로그인 성공 (= 비밀번호 일치)
		if(passwordEncoder.matches(userDTO.getPassword(), password)) {
			// 토큰 생성 및 전달
			Map<String, Object> userInfo = userService.getByUser(email);
			
			String token = securityService.createToken(email, (120 * 1000 * 60), userInfo);
	        map.put("token", token);
	        return map;
			
		} else {
			return null;
		}
	}
	
	// 유저정보 가져오기
	@RequestMapping(value = "/get/subject", method = RequestMethod.GET)
	public Map<String, Object> getSubject(@RequestHeader("access_token") String token) throws Exception {
			
		Map<String, Object> subject = securityService.getSubject(token);
        return subject;
	}
	
	// 마이페이지 -> 내 게시글 가져오기
	@RequestMapping(value = "/api/user/getMyBoardList", method = RequestMethod.POST)
	public Map<String, Object> getMyBoardList(@RequestBody Map<String,Object> getMyBoard) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			// 내 게시글 리스트 가져오기
			result.put("MyBoardList", userService.getMyBoardList(getMyBoard));
			
			// total 값 가져오기
			result.put("total", userService.getMyBoardListTotal(getMyBoard));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 마이페이지 -> 내 댓글 가져오기
	@RequestMapping(value = "/api/user/getMyReplyList", method = RequestMethod.POST)
	public Map<String, Object> getMyReplyList(@RequestBody Map<String,Object> getMyReply) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			// 내 댓글 리스트 가져오기
			result.put("MyReplyList", userService.getMyReplyList(getMyReply));
			
			// total 값 가져오기
			result.put("total", userService.getMyReplyListTotal(getMyReply));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 마이페이지 -> 정보 수정 -> 비밀번호 인증
	@RequestMapping(value = "/api/user/passwordCerti", method = RequestMethod.POST)
	public int passwordCerti(@RequestBody UserDTO userDTO) {
		int result = 0;
		try {
			// 이메일로 비밀번호 가져오기
			Map<String, Object> getPassword = userService.login(userDTO);
			
			String password = (String)getPassword.get("password");
			
			// 비밀번호가 일치하면
			if(passwordEncoder.matches(userDTO.getPassword(), password)) {
				result = 1;
			// 비밀번호가 일치하지 않을 때
			}else {
				result = 0;
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 마이페이지 -> 정보 수정
	@RequestMapping(value = "/api/user/changMemberInfo", method = RequestMethod.POST)
	public Map<String, Object> changMemberInfo(@RequestBody UserDTO userDTO) {
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		String email = userDTO.getEmail();
		Integer id = userDTO.getId();
		try {
			// 정보 변경하기
			userService.changMemberInfo(userDTO);
			
			// 유저 정보 가져오기
			Map<String, Object> userInfo = userService.getUserById(id);
			
			// 토큰 발급
			String token = securityService.createToken(email, (120 * 1000 * 60), userInfo);
			result.put("token", token);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 마이페이지 -> 회원 탈퇴
	@RequestMapping(value = "/api/user/deleteMemberInfo", method = RequestMethod.POST)
	public void deleteMemberInfo(@RequestBody UserDTO userDTO) {
		
		try {
			// 정보 변경하기
			userService.deleteMemberInfo(userDTO);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	// 관리자 페이지 -> 회원 정보 가져오기
	@RequestMapping(value = "/api/user/adminGetUser", method = RequestMethod.GET)
	public Map<String, Object> adminGetUser(@RequestParam("currentPage") int currentPage) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 유저 리스트 가져오기
			result.put("userList", userService.adminGetUser(currentPage));
			
			// 유저 리스트 토탈 가져오기
			result.put("total", userService.adminGetUserTotal());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 관리자 페이지 -> 회원 정보 수정
	@RequestMapping(value = "/api/user/adminChangeUser", method = RequestMethod.POST)
	public void adminChangeUser(@RequestBody UserDTO userDTO) {
		
		try {
			// 정보 변경하기
			userService.adminChangeUser(userDTO);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	// 관리자 페이지 -> 회원 탈퇴
	@RequestMapping(value = "/api/user/adminDeleteUser", method = RequestMethod.POST)
	public void adminDeleteUser(@RequestBody UserDTO userDTO) {
		
		try {
			// 정보 변경하기
			userService.adminDeleteUser(userDTO);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
}
