package com.cts.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cts.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PharmacyAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(PharmacyAuthenticationProvider.class);

	private UserService userService;
	private BCryptPasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("Authenticating");
		String username = authentication.getName();
		UserDetails userDetails = userService.loadUserByUsername(username);
		if (encoder.matches((String) authentication.getCredentials(), userDetails.getPassword())) {
			log.info("Passwords matched");
			return new UsernamePasswordAuthenticationToken(username, (String) authentication.getCredentials(),
					userDetails.getAuthorities());
		} else {
			log.error("Username/Password didn't match");
			throw new BadCredentialsException("Bad Credentials");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
