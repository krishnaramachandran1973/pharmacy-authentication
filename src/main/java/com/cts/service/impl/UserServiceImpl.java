package com.cts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.domain.PharmacyUser;
import com.cts.domain.Role;
import com.cts.repository.PharmacyUserRepository;
import com.cts.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private PharmacyUserRepository pharmacyUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("User is loaded from database");
		PharmacyUser user = pharmacyUserRepository.findByUsername(username);

		List<GrantedAuthority> roles = getUserAuthority(user.getRoles());

		UserDetails authenticatedUser = buildUserForAuthentication(user, roles);
		return authenticatedUser;
	}

	private UserDetails buildUserForAuthentication(PharmacyUser user, List<GrantedAuthority> roles) {
		return new User(user.getUsername(), user.getPassword(), user.getActive(), true, true, true, roles);
	}

	private List<GrantedAuthority> getUserAuthority(List<Role> roles) {
		List<GrantedAuthority> grantedRoles = new ArrayList<>();
		roles.forEach(role -> grantedRoles.add(new SimpleGrantedAuthority(role.getRoleName())));
		return grantedRoles;
	}

}
