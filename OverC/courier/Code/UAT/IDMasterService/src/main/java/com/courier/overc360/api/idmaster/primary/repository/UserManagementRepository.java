package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.user.UserManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserManagementRepository extends JpaRepository<UserManagement, Long>, JpaSpecificationExecutor<UserManagement> {
	
	List<UserManagement> findByUserIdAndDeletionIndicator(String userId, Long deletionIndicator);

	Optional<UserManagement> findByLanguageIdAndCompanyIdAndUserIdAndDeletionIndicator(
			String languageId, String companyId, String userId, Long deletionIndicator);
}