package com.springbootvue.project.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootvue.project.dao.BoardMapper;
import com.springbootvue.project.dto.BoardDTO;
import com.springbootvue.project.dto.ReplyDTO;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardMapper boardMapper;

	// 글 쓰기
	@Override
	public void write(BoardDTO boardDTO) throws Exception {

		boardMapper.write(boardDTO);
	}
	
	// 글 가져오기
	@Override
	public List<Map<String, Object>> getBoard(Map<String, Object> boardSearch) throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

		
		// 페이징 처리
		int currentPage = (int) boardSearch.get("currentPage");
		currentPage = (currentPage -1 ) * 10;
		boardSearch.put("currentPage", currentPage);
		
		// 게시글 가져오기
		List<Map<String, Object>> result = boardMapper.getBoard(boardSearch);
		
		// 날짜 포맷 변환
		for(Map<String, Object> map : result) {
			map.put("regdate", simpleDateFormat.format(map.get("regdate")));
		}
		
		return result;
	}
	
	// 게시글 total 가져오기
	@Override
	public int getBoardTotal() throws Exception {
		
		int result = boardMapper.getBoardTotal();
		
		return result;
	}
	
	// 글 수정
	@Override
	public void edit(BoardDTO boardDTO) throws Exception {
		
		boardMapper.edit(boardDTO);
	}
	
	// 글 삭제
	@Override
	public void delete(BoardDTO boardDTO) throws Exception {
		
		boardMapper.delete(boardDTO);
	}
	
	// 조회수 올리기
	public void updateReadCount(BoardDTO boardDTO) throws Exception {
		
		boardMapper.updateReadCount(boardDTO);
	}
	
	// 조회수 가져오기
	@Override
	public int getBoardReadCount(BoardDTO boardDTO) throws Exception {

		int result = boardMapper.getBoardReadCount(boardDTO);
		
		return result;
	}
	
	// 댓글 작성하기
	@Override
	public void regReply(ReplyDTO replyDTO) throws Exception {
		
		boardMapper.regReply(replyDTO);
	}
	
	// 댓글 가져오기
	@Override
	public List<Map<String, Object>> getReply(ReplyDTO replyDTO) throws Exception {
		
		List<Map<String, Object>> result = boardMapper.getReply(replyDTO);
		
		return result;
	}
	
	// 댓글 총 개수 가져오기
	@Override
	public int getReplyTotal(ReplyDTO replyDTO) throws Exception {
		int result = boardMapper.getReplyTotal(replyDTO);
		return result;
	}
	
	// 댓글 수정하기
	@Override
	public void editReply(ReplyDTO replyDTO) throws Exception {
		boardMapper.editReply(replyDTO);
		
	}
	
	// 댓글 삭제하기
	@Override
	public void deleteReply(ReplyDTO replyDTO) throws Exception {
		boardMapper.deleteReply(replyDTO);
		
	}
	
	// 게시글 추천 중복확인
	@Override
	public List<Map<String, Object>> liked(Map<String, Object> likedInfo) throws Exception {
		
		List<Map<String, Object>> result = boardMapper.liked(likedInfo);
		
		return result;
	}
	
	// 게시글 추천 DB에 넣기
	@Override
	public void addliked(Map<String, Object> likedInfo) throws Exception {
		boardMapper.addliked(likedInfo);
		
	}
	
	// 게시글 추천 더하기
	@Override
	public void updateliked(Map<String, Object> likedInfo) throws Exception {
		boardMapper.updateliked(likedInfo);
		
	}
	// 게시글 추천수 가져오기
	@Override
	public int getliked(BoardDTO boardDTO) throws Exception {
		
		int result = boardMapper.getliked(boardDTO);
		
		return result;
	}
	
	// 인기 게시글 가져오기
	@Override
	public List<Map<String, Object>> getPopList() throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
		
		List<Map<String, Object>> result = boardMapper.getPopList();
		
		for(Map<String, Object> map : result) {
			map.put("regdate", simpleDateFormat.format(map.get("regdate")));
		}
		
		return result;
	}
}
