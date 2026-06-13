package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.roleaccess.ReplicaRoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaRoleAccessRepository extends JpaRepository<ReplicaRoleAccess, Long>, JpaSpecificationExecutor<ReplicaRoleAccess> {

    Optional<ReplicaRoleAccess> findByLanguageIdAndCompanyIdAndRoleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
            String languageId, String companyId, Long roleId, Long menuId, Long subMenuId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndRoleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
            String languageId, String companyId, Long roleId, Long menuId, Long subMenuId, Long deletionIndicator);

    List<ReplicaRoleAccess> findByLanguageIdAndCompanyIdAndRoleIdAndDeletionIndicator(
            String languageId, String companyId, Long roleId, Long deletionIndicator);

    @Query(value = "Select \n" +
            "CONCAT (tl.USR_ROLE_ID, ' - ', tl.USR_ROLE_NM) As userRoleDesc \n" +
            "From tblroleaccess tl \n" +
            "WHERE \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tl.C_ID IN (:companyId) and \n" +
            "tl.USR_ROLE_ID IN (:userRoleId) and \n" +
            "tl.IS_DELETED=0 ", nativeQuery = true)
    IKeyValuePair getRoleDesc(@Param(value = "languageId") String languageId,
                              @Param(value = "companyId") String companyId,
                              @Param(value = "userRoleId") Long userRoleId);


    // Find RoleAccesses with given Params Only
    @Query(value = "SELECT * FROM tblroleaccess tr \n" +
            "WHERE tr.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:menuId, NULL) IS NULL OR tr.MENU_ID IN (:menuId)) \n" +
            "AND (COALESCE(:subMenuId, NULL) IS NULL OR tr.SUB_MENU_ID IN (:subMenuId)) \n" +
            "AND (COALESCE(:statusId, NULL) IS NULL OR tr.STATUS_ID IN (:statusId)) \n" +
            "AND (COALESCE(:roleId, NULL) IS NULL OR tr.ROLE_ID IN (:roleId))", nativeQuery = true)
    List<ReplicaRoleAccess> findRoleAccessesWithQry(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("menuId") List<Long> menuId,
            @Param("subMenuId") List<Long> subMenuId,
            @Param("statusId") List<String> statusId,
            @Param("roleId") List<Long> roleId);

}

