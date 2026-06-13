package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.ReplicaCustomsClearanceInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCustomsClearanceInvoiceRepository extends JpaRepository<ReplicaCustomsClearanceInvoice, Long>, JpaSpecificationExecutor<ReplicaCustomsClearanceInvoice> {

    /*
     * `LANG_ID`, `C_ID`, `PARTNER_HOUSE_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`,`INVOICE_NO`
     */

    Optional<ReplicaCustomsClearanceInvoice> findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(
            String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo, Long deletionIndicator
    );

    boolean existsByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(
            String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo, Long deletionIndicator);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID=tc.LANG_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId);
}
