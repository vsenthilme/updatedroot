package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.module.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ModuleRepository extends JpaRepository<Module, Long>, JpaSpecificationExecutor<Module> {

    Optional<Module> findByCompanyIdAndAndLanguageIdAndModuleIdAndMenuIdAndSubMenuIdAndDeletionIndicator
            (String companyId, String languageId, String moduleId, Long menuId, Long subMenuId, Long deletionIndicator);

    List<Module> findByCompanyIdAndModuleIdAndLanguageIdAndDeletionIndicator
            (String companyId, String moduleId, String languageId, Long deletionIndicator);

}