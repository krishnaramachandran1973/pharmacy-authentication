package com.cts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.domain.AuthenticationResponse;
import com.cts.domain.PharmacyUser;
import com.cts.service.impl.PharmacyAuthenticationProvider;
import com.cts.util.JwtUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/security")
public class AuthenticationController {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	private PharmacyAuthenticationProvider provider;
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody PharmacyUser user) {
		log.info(user.toString());
		Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());
		try {
			Authentication authenticatedUser = provider.authenticate(usernamePasswordAuthenticationToken);
			String token = jwtUtil.generateToken(authenticatedUser);
			return ResponseEntity.ok()
					.body(AuthenticationResponse.builder()
							.jwt(token)
							.build());

		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

	@GetMapping("/validate/{token}")
	public ResponseEntity<?> validate(@PathVariable String token) {
		log.info("Received token {}", token);
		Boolean value = false;
		try {
			value = jwtUtil.validateToken(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.build();
		}
		return ResponseEntity.ok(value);
	}

}
