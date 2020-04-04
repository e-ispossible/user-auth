package com.salle.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salle.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public boolean existsByEmail(String email);
	public Optional<User> findByEmail(String email);
}
