package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.user.ReplicaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaUserRepository extends JpaRepository<ReplicaUser, Long>{

	public List<ReplicaUser> findAll();
	Optional<ReplicaUser> findByEmail(String email);
	Optional<ReplicaUser> findByUsername(String username);
}