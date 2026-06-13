package com.courier.overc360.api.repository;

import com.courier.overc360.api.model.user.NewUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegisterRepository extends CrudRepository<NewUser, Long> {

	Optional<NewUser> findByRegisterId(String id);
	Optional<NewUser> findByClientName(String clientName);
}