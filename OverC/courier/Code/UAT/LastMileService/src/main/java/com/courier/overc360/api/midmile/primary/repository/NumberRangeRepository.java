package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.numberange.NumberRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface NumberRangeRepository extends JpaRepository<NumberRange, String>, JpaSpecificationExecutor<NumberRange> {

    Optional<NumberRange> findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
            String languageId, Long numberRangeCode, String numberRangeObject, Long deletionIndicator);

    Optional<NumberRange> findByNumberRangeCode(Long numberRangeCode);

    Optional<NumberRange> findByNumberRangeCodeAndNumberRangeObjectAndLanguageId(
            Long numberRangeCode, String numberRangeObject, String languageId);

    Optional<NumberRange> findByNumberRangeObjectAndDeletionIndicator(String numberRangeObject, Long deletionIndicator);
}
