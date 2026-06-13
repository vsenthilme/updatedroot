package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.movingshipmentpricelist.MovingShipmentPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface MovingShipmentPriceListRepository extends JpaRepository<MovingShipmentPriceList, String>, JpaSpecificationExecutor<MovingShipmentPriceList> {

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);

    Optional<MovingShipmentPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);

}
