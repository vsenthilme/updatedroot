package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.CustomsClearanceInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface CustomsClearanceInvoiceRepository extends JpaRepository<CustomsClearanceInvoice, Long>, JpaSpecificationExecutor<CustomsClearanceInvoice> {

    /*
     * `LANG_ID`, `C_ID`, `PARTNER_HOUSE_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`,`INVOICE_NO`
     */

    Optional<CustomsClearanceInvoice> findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(
            String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoice, Long deletionIndicator
    );

//    @Query()
//    public void destinationDetails(@Param("destinationName") String destinationName,
//                                   @Param("destinationAddress") String destinationAddress);

    @Modifying
    @Query(value = "UPDATE tblcustomsclearanceinvoice SET special_approval_value = coalesce(:newAmount, 0.0) " +
            " WHERE lang_id = :languageId AND c_id = :companyId AND PARTNER_HOUSE_AIRWAY_BILL = :invoiceNumber" +
            " AND is_deleted = 0 ", nativeQuery = true)
    void updateClearanceInvoiceAmount(@Param("newAmount") Double newAmount,
                                      @Param("languageId") String languageId,
                                      @Param("companyId") String companyId,
                                      @Param("invoiceNumber") String invoiceNumber);

}
