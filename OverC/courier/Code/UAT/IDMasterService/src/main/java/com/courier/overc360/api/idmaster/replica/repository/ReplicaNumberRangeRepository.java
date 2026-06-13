package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.primary.model.numberange.NumberRange;
import com.courier.overc360.api.idmaster.replica.model.numberrange.ReplicaNumberRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaNumberRangeRepository extends JpaRepository<ReplicaNumberRange, String>, JpaSpecificationExecutor<ReplicaNumberRange> {

    Optional<ReplicaNumberRange> findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
            String languageId, Long numberRangeCode, String numberRangeObject, Long deletionIndicator);

    Optional<NumberRange> findByNumberRangeCode(Long numberRangeCode);

    Optional<NumberRange> findByNumberRangeCodeAndNumberRangeObjectAndLanguageId(
            Long numberRangeCode, String numberRangeObject, String languageId);

    Optional<NumberRange> findByNumberRangeObjectAndDeletionIndicator(String numberRangeObject, Long deletionIndicator);
}
