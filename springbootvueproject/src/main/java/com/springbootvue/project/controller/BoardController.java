package com.springbootvue.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootvue.project.dto.BoardDTO;
import com.springbootvue.project.dto.ReplyDTO;
import com.springbootvue.project.service.BoardService;

@RestController
public class BoardController {

	@Autowired
	BoardService boardService;
	
	// 글 쓰기
	@RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
	public void writeBoard(@RequestBody BoardDTO boardDTO) {
		
		try {
			
			boardService.write(boardDTO);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	// 자유 게시판 리스트 조회
	@RequestMapping(value = "/api/board/getFreeList", method = RequestMethod.POST)
	public Map<String, Object> getBoard(@RequestBody Map<String, Object> boardSearch) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			// 게시글 리스트
			result.put("FreeBoardList", boardService.getBoard(boardSearch)); 
			
			// 게시글 total 가져오기
			result.put("total", boardService.getBoardTotal());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 글 수정
	@RequestMapping(value = "/api/board/edit", method = RequestMethod.POST)
	public void editBoard(@RequestBody BoardDTO boardDTO) {
		
		try {
			boardService.edit(boardDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 글 삭제
	@RequestMapping(value = "/api/board/delete", method = RequestMethod.POST)
	public void deleteBoard(@RequestBody BoardDTO boardDTO) {
		
		try {
			boardService.delete(boardDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 조회수 증가
	@RequestMapping(value = "/api/board/updateReadCount", method = RequestMethod.POST)
	public int updateReadCount(@RequestBody BoardDTO boardDTO) {
		int readCount = 0;
		try {
			// 게시글 조회수 올리기
			boardService.updateReadCount(boardDTO);
			
			// 번호에 맞는 게시글의 조회수 가져오기
			readCount = boardService.getBoardReadCount(boardDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readCount;
	}
	
	// 댓글 등록
	@RequestMapping(value = "/api/board/regReply", method = RequestMethod.POST)
	public void regReply(@RequestBody ReplyDTO replyDTO) {
		try {
			
			boardService.regReply(replyDTO);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	// 댓글 가져오기
	@RequestMapping(value = "/api/board/getReply", method = RequestMethod.POST)
	public Map<String, Object> getReply(@RequestBody ReplyDTO replyDTO) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			// 댓글 리스트
			result.put("replyList", boardService.getReply(replyDTO));
			
			// 댓글 총 개수
			result.put("total", boardService.getReplyTotal(replyDTO));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 댓글 수정하기
	@RequestMapping(value = "/api/board/editReply", method = RequestMethod.POST)
	public void editReply(@RequestBody ReplyDTO replyDTO) {
		
		try {
			
			boardService.editReply(replyDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 댓글 삭제하기
	@RequestMapping(value = "/api/board/deleteReply", method = RequestMethod.POST)
	public void deleteReply(@RequestBody ReplyDTO replyDTO) {
		
		try {
			
			boardService.deleteReply(replyDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 게시글 추천 기능
	@RequestMapping(value = "/api/board/liked", method = RequestMethod.POST)
	public int liked(@RequestBody Map<String, Object> likedInfo) {
		int result = 0;
		
		try {
			// 이미 추천 누른 회원인지 검사
			List<Map<String, Object>> check = boardService.liked(likedInfo);
			
			if(check.size() > 0) {
				// 추천 눌렀었음
				result = 0;
			}else {
				// 추천 누른 적 없음

				// 게시글 추천수 올리기
				boardService.updateliked(likedInfo);
				
				// DB에 추가하는 기능 넣기
				boardService.addliked(likedInfo);
				
				result = 1;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	// 게시글 추천수 가져오기
	@RequestMapping(value = "/api/board/getliked", method = RequestMethod.POST)
	public int getliked(@RequestBody BoardDTO boardDTO) {
		
		int result = 0;
		
		try {
			// 추천수 가져오기
			result = boardService.getliked(boardDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 인기 게시판 게시글 가져오기
	@RequestMapping(value = "/api/board/getPopList", method = RequestMethod.GET)
	public List<Map<String, Object>> getPopList() {
		
		List<Map<String, Object>> result = null;
		
		try {
			// 리스트 가져오기
			result = boardService.getPopList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
}
