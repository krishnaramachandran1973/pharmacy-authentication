package com.cts.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	private static final String SECRET = "secret";

	public String generateToken(Authentication user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", user.getName());

		log.info("Creating the JWT");
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getName())
				.setIssuer("cts.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody();
	}

	public String extractUsername(String token) {
		log.info("Extracting the subject");
		return getClaims(token).getSubject();
	}

	public Boolean validateToken(String token) {
		log.info("Checking token validity");
		return !getClaims(token).getExpiration()
				.before(new Date());
	}

}
