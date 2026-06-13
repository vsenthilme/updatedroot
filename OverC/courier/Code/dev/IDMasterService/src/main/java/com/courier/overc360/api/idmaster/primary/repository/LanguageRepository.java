package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface LanguageRepository extends JpaRepository<Language, String>, JpaSpecificationExecutor<Language> {

    Optional<Language> findByLanguageIdAndDeletionIndicator(String languageId, Long deletionIndicator);


    // Update Language Description in all Masters Tables
    @Transactional
    @Procedure(procedureName = "language_desc_update_proc")
    void updateLanguageDescProc(
            @Param(value = "languageId") String languageId,
            @Param(value = "oldLanguageDesc") String oldLanguageDesc,
            @Param(value = "newLanguageDesc") String newLanguageDesc);


    // Delete Validation Query for Language Table
    @Query(value = "Select COUNT (*) From ( \n" +
            "Select 1 As col From tblcompany Where LANG_ID IN (:languageId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblstatus Where LANG_ID IN (:languageId) and IS_DELETED = 0 \n" +
            ") As temp", nativeQuery = true)
    Long getLanguageCount(@Param(value = "languageId") String languageId);

}
