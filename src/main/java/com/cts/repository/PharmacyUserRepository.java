package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.domain.PharmacyUser;

public interface PharmacyUserRepository extends JpaRepository<PharmacyUser, Long> {
	PharmacyUser findByUsername(String username);

}
