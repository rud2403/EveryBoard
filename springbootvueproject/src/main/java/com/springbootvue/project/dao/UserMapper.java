package com.springbootvue.project.dao;

import java.util.List;
import java.util.Map;

import com.springbootvue.project.dto.UserDTO;

public interface UserMapper {

	// 회원가입
	public void signUp(UserDTO userDTO) throws Exception;
	
	// 로그인
	public Map<String, Object> login(UserDTO userDTO) throws Exception;
	
	// 유저 정보 가져오기
	public Map<String, Object> getByUser(String email) throws Exception;
	
	// 마이페이지 -> 내 게시글 가져오기
	public List<Map<String, Object>> getMyBoardList(Map<String, Object> getMyBoard) throws Exception;
	
	// 마이페이지 -> 내 게시글 total 가져오기
	public int getMyBoardListTotal(Map<String, Object> getMyBoard) throws Exception;
	
	// 마이페이지 -> 내 댓글 가져오기
	public List<Map<String, Object>> getMyReplyList(Map<String, Object> getMyReply) throws Exception;
	
	// 마이페이지 -> 내 댓글 total 가져오기
	public int getMyReplyListTotal(Map<String, Object> getMyReply) throws Exception;
	
	// 마이페이지 -> 정보 수정
	public void changMemberInfo(UserDTO userDTO) throws Exception;
	
	// 아이디로 유저 정보 가져오기
	public Map<String, Object> getUserById(Integer id) throws Exception;
	
	// 마이페이지 -> 회원 탈퇴
	public void deleteMemberInfo(UserDTO userDTO) throws Exception;
	
	// 관리자페이지 -> 유저 관리 ( 유저 리스트 가져오기 )
	public List<Map<String, Object>> adminGetUser(int currentPage) throws Exception;
	
	// 관리자 페이지 -> 유저 리스트 토탈 가져오기
	public int adminGetUserTotal() throws Exception;
	
	// 관리자 페이지 -> 회원 정보 수정
	public void adminChangeUser(UserDTO userDTO) throws Exception;
	
	// 관리자 페이지 -> 회원 탈퇴
	public void adminDeleteUser(UserDTO userDTO) throws Exception;
}
