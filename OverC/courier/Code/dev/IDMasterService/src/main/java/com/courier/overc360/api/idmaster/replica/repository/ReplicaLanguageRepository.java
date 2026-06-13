package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.language.ReplicaLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaLanguageRepository extends JpaRepository<ReplicaLanguage, String>, JpaSpecificationExecutor<ReplicaLanguage> {

    Optional<ReplicaLanguage> findByLanguageIdAndDeletionIndicator(String languageId, Long deletionIndicator);

    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc \n" +
            "From tbllanguage tl \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tl.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId);


}
