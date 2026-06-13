package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.menu.ReplicaMenu;
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
public interface ReplicaMenuRepository extends JpaRepository<ReplicaMenu, Long>, JpaSpecificationExecutor<ReplicaMenu> {

    Optional<ReplicaMenu> findByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndAuthorizationObjectIdAndDeletionIndicator(
            String languageId, String companyId, Long menuId, Long subMenuId, Long authorizationObjectId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
            String languageId, String companyId, Long menuId, Long subMenuId, Long deletionIndicator);

    List<ReplicaMenu> findByMenuId(Long menuId);

    List<ReplicaMenu> findBySubMenuId(Long subMenuId);


    // Get All Non-Deleted Menus
    @Query(value = "Select * From tblmenu \n" +
            "Where IS_DELETED = 0", nativeQuery = true)
    List<ReplicaMenu> getNonDeletedMenus();


    // Find Menus with given Params Only
    @Query(value = "SELECT * FROM tblmenu tm \n" +
            "WHERE tm.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tm.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tm.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:menuId, NULL) IS NULL OR tm.MENU_ID IN (:menuId)) \n" +
            "AND (COALESCE(:subMenuId, NULL) IS NULL OR tm.SUB_MENU_ID IN (:subMenuId)) \n" +
            "AND (COALESCE(:statusId, NULL) IS NULL OR tm.STATUS_ID IN (:statusId)) \n" +
            "AND (COALESCE(:authorizationObjectId, NULL) IS NULL OR tm.AUT_OBJ_ID IN (:authorizationObjectId))", nativeQuery = true)
    List<ReplicaMenu> findMenusWithQry(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("menuId") List<Long> menuId,
            @Param("subMenuId") List<Long> subMenuId,
            @Param("statusId") List<String> statusId,
            @Param("authorizationObjectId") List<Long> authorizationObjectId);


}