package com.springbootvue.project.dao;

import java.util.List;
import java.util.Map;

import com.springbootvue.project.dto.BoardDTO;
import com.springbootvue.project.dto.ReplyDTO;

public interface BoardMapper {
	
	// 글 쓰기
	public void write(BoardDTO boardDTO) throws Exception;
	
	// 글 가져오기
	public List<Map<String, Object>> getBoard(Map<String, Object> boardSearch) throws Exception;

	// 글 total 가져오기
	public int getBoardTotal() throws Exception;
	
	// 글 수정
	public void edit(BoardDTO boardDTO) throws Exception;
	
	// 글 삭제
	public void delete(BoardDTO boardDTO) throws Exception;
	
	// 조회수 올리기
	public void updateReadCount(BoardDTO boardDTO) throws Exception;
	
	// 조회수 가져오기
	public int getBoardReadCount(BoardDTO boardDTO) throws Exception;
	
	// 댓글 작성하기
	public void regReply(ReplyDTO replyDTO) throws Exception;
	
	// 댓글 가져오기
	public List<Map<String, Object>> getReply(ReplyDTO replyDTO) throws Exception;
	
	// 댓글 총 개수 가져오기
	public int getReplyTotal(ReplyDTO replyDTO) throws Exception;
	
	// 댓글 수정하기
	public void editReply(ReplyDTO replyDTO) throws Exception;
	
	// 댓글 삭제하기
	public void deleteReply(ReplyDTO replyDTO) throws Exception;
	
	// 게시글 추천 중복확인
	public List<Map<String, Object>> liked(Map<String,Object> likedInfo) throws Exception;
	
	// 게시글 추천 기능
	public void addliked(Map<String,Object> likedInfo) throws Exception;
	
	// 게시글 추천수 더하기
	public void updateliked(Map<String,Object> likedInfo) throws Exception;
	
	// 게시글 추천수 가져오기
	public int getliked(BoardDTO boardDTO) throws Exception;
	
	// 인기 게시판 게시글 가져오기
	public List<Map<String, Object>> getPopList() throws Exception;;
}
