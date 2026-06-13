package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.paymentType.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional
public interface PaymentTypeRepository extends JpaRepository<PaymentType, String>, JpaSpecificationExecutor<PaymentType> {


    boolean existsByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(String languageId, String companyId, String paymentTypeId, long l);

    Optional<PaymentType> findByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(String languageId, String companyId, String paymentTypeId, long l);
}

