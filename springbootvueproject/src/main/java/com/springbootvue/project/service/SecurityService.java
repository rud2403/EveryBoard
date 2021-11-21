package com.springbootvue.project.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.springbootvue.project.dto.UserDTO;

@Service
public interface SecurityService {
	// 토큰 생성
    String createToken(String subject, long ttlMillis, Map<String, Object> payload);
 
    // 토큰 검증
    Map<String, Object> getSubject(String token);
}
