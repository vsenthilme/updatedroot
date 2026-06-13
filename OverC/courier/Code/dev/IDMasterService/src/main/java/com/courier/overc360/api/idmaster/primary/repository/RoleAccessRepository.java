package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.roleaccess.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RoleAccessRepository extends JpaRepository<RoleAccess, Long>,
        JpaSpecificationExecutor<RoleAccess> {

    Optional<RoleAccess> findByCompanyIdAndAndLanguageIdAndRoleIdAndMenuIdAndSubMenuIdAndDeletionIndicator
            (String companyId, String languageId, Long roleId, Long menuId, Long subMenuId, Long deletionIndicator);

    List<RoleAccess> findByCompanyIdAndRoleIdAndLanguageIdAndDeletionIndicator
            (String companyId, Long roleId, String languageId, Long deletionIndicator);

    @Query(value = "Select \n" +
            "Max(role_id) +1 roleId  \n" +
            "From tblroleaccess", nativeQuery = true)
    Long getRoleId();


}
