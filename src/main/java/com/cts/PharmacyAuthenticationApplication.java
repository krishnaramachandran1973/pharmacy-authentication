package com.cts;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.domain.PharmacyUser;
import com.cts.domain.Role;
import com.cts.repository.PharmacyUserRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class PharmacyAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyAuthenticationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PharmacyUserRepository repo, BCryptPasswordEncoder encoder) {
		return args -> {
			Role role = Role.builder()
					.roleName("ROLE_USER")
					.build();

			PharmacyUser user = PharmacyUser.builder()
					.username("user")
					.password(encoder.encode("user"))
					.active(true)
					.roles(Arrays.asList(role))
					.build();

			repo.save(user);
		};
	}

}
