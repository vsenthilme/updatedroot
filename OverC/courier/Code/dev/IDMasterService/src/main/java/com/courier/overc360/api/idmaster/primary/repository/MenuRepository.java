package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    Optional<Menu> findByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndAuthorizationObjectIdAndDeletionIndicator(
            String languageId, String companyId, Long menuId, Long subMenuId, Long authorizationObjectId, Long deletionIndicator);
    Optional<Menu> findByCompanyIdAndAndLanguageIdAndMenuIdAndSubMenuIdAndDeletionIndicator
            (String companyId, String languageId, Long menuId, Long subMenuId, Long deletionIndicator);
    List<Menu> findByMenuId(Long menuId);

    List<Menu> findBySubMenuId(Long subMenuId);

}