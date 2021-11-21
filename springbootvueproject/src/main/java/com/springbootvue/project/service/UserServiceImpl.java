package com.springbootvue.project.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootvue.project.dao.UserMapper;
import com.springbootvue.project.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	// 회원가입
	@Override
	public void signUp(UserDTO userDTO) throws Exception {
		
		// password 암호화
		String password = userDTO.getPassword();
		String encodePassword = passwordEncoder.encode(password);
		userDTO.setPassword(encodePassword);
		
		userMapper.signUp(userDTO);
	}
	
	// 로그인
	@Override
	public Map<String, Object> login(UserDTO userDTO) throws Exception {

		Map<String, Object> result = userMapper.login(userDTO);
		
		return result;
	}
	
	// 유저 정보 가져오기
	@Override
	public Map<String, Object> getByUser(String email) throws Exception {

		Map<String, Object> result = userMapper.getByUser(email);
		
		return result;
	}
	
	// 마이페이지 -> 내 게시글 가져오기
	@Override
	public List<Map<String, Object>> getMyBoardList(Map<String, Object> getMyBoard) throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
		
		// 페이징 처리
		int currentPage = (int) getMyBoard.get("currentPage");
		currentPage = (currentPage -1 ) * 10;
		getMyBoard.put("currentPage", currentPage);
		
		List<Map<String, Object>> result = userMapper.getMyBoardList(getMyBoard);
		
		// 날짜 포맷
		for(Map<String, Object> map : result) {
			map.put("regdate", simpleDateFormat.format(map.get("regdate")));
		}
		
		return result;
	}
	
	// 마이페이지 -> 내 게시글 total 가져오기
	@Override
	public int getMyBoardListTotal(Map<String, Object> getMyBoard) throws Exception {

		int result = userMapper.getMyBoardListTotal(getMyBoard);
		
		return result;
	}
	
	// 마이페이지 -> 내 댓글 가져오기
	@Override
	public List<Map<String, Object>> getMyReplyList(Map<String, Object> getMyReply) throws Exception {
		
		// 페이징 처리
		int currentPage = (int) getMyReply.get("currentPage");
		currentPage = (currentPage -1 ) * 10;
		getMyReply.put("currentPage", currentPage);
		
		List<Map<String, Object>> result = userMapper.getMyReplyList(getMyReply);
		
		return result;
	}
	
	// 마이페이지 -> 내 댓글 total 가져오기
	@Override
	public int getMyReplyListTotal(Map<String, Object> getMyReply) throws Exception {
		
		int result = userMapper.getMyReplyListTotal(getMyReply);
		
		return result;
	}
	
	// 마이페이지 -> 정보 수정
	@Override
	public void changMemberInfo(UserDTO userDTO) throws Exception {
		
		userMapper.changMemberInfo(userDTO);
		
	}
	
	// 아이디로 유저 정보 가져오기
	@Override
	public Map<String, Object> getUserById(Integer id) throws Exception {
		
		Map<String, Object> result = userMapper.getUserById(id);
		
		return result;
	}
	
	// 마이페이지 -> 회원 탈퇴
	@Override
	public void deleteMemberInfo(UserDTO userDTO) throws Exception {
		
		userMapper.deleteMemberInfo(userDTO);
		
	}
	
	// 관리자 페이지 -> 유저관리 ( 유저 리스트 가져오기 )
	@Override
	public List<Map<String, Object>> adminGetUser(int currentPage) throws Exception {
		
		// 페이징 처리
		currentPage = (currentPage - 1 ) * 10;
		
		List<Map<String, Object>> result = userMapper.adminGetUser(currentPage);
		
		return result;
	}
	
	// 관리자 페이지 -> 유저 리스트 토탈 가져오기
	@Override
	public int adminGetUserTotal() throws Exception {

		int result = userMapper.adminGetUserTotal();
		
		return result;
	}
	
	// 관리자 페이지 -> 회원 정보 수정
	@Override
	public void adminChangeUser(UserDTO userDTO) throws Exception {

		userMapper.adminChangeUser(userDTO);
		
	}
	
	// 관리자 페이지 -> 회원 탈퇴
	@Override
	public void adminDeleteUser(UserDTO userDTO) throws Exception {

		userMapper.adminDeleteUser(userDTO);
		
	}
}
