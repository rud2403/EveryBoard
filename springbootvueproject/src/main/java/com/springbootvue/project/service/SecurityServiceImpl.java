package com.springbootvue.project.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.springbootvue.project.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityServiceImpl implements SecurityService{
	private static final String SECRET_KEY = "aasjjkjaskjdl1k2naskjkdakj34c8sa";

	Date now = new Date();
	
	@Override
	public String createToken(String subject, long ttlMillis, Map<String, Object> payload) {
		if (ttlMillis <= 0) {
            throw new RuntimeException("Expiry time must be greater than Zero : ["+ttlMillis+"] ");
        }
		
		// 토큰을 서명하기 위해 사용해야할 알고리즘 선택
        SignatureAlgorithm  signatureAlgorithm = SignatureAlgorithm.HS256;
 
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
						.setSubject(subject)
						.setClaims(payload)
						.signWith(signatureAlgorithm, signingKey)
						.setIssuedAt(now) // 토큰 발급시간
						.setExpiration(new Date(System.currentTimeMillis()+ttlMillis)) // 토큰 만료시간
						.compact();
		}
	
	@Override
	public Map<String, Object> getSubject(String token) {
		
		Map<String, Object> claimMap = null;
		
		Claims claims = Jwts.parser()
							// 시크릿 키로 토큰 해석
							.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
							// 해석할 토큰을 문자열로 넣어준다
							.parseClaimsJws(token)
							.getBody();
		
		claimMap = claims;
		
		return claimMap;
	}
}
