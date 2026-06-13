package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.bondedmanifest.ReplicaBondedManifest;
import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaConsignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaBondedManifestRepository extends JpaRepository<ReplicaBondedManifest, String>,
        JpaSpecificationExecutor<ReplicaBondedManifest> {

    Optional<ReplicaBondedManifest> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndBondedIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String bondedId, Long deletionIndicator);

    // Company Table records check
    @Query(value = "Select COUNT (*) From tblcompany \n" +
            "Where \n" +
            "LANG_ID IN (:languageId) and \n" +
            "C_ID IN (:companyId) and \n" +
            "IS_DELETED = 0", nativeQuery = true)
    Long companyRecordCount(@Param(value = "languageId") String languageId,
                            @Param(value = "companyId") String companyId);

    // Duplicate  Check
    @Query(value = "Select \n" +
            "Case When Exists \n" +
            "(Select 1 From tblbondedmanifest h \n" +
            "Where \n" +
            "h.LANG_ID = :languageId and \n" +
            "h.C_ID = :companyId and \n" +
            "h.PARTNER_ID = :partnerId and \n" +
            "h.MASTER_AIRWAY_BILL = :masterAirwayBill and \n" +
            "h.HOUSE_AIRWAY_BILL = :houseAirwayBill and \n" +
            "h.IS_DELETED = 0) \n" +
            "Then 1 \n" +
            "Else 0 \n" +
            "End", nativeQuery = true)
    Long duplicateExists(@Param(value = "languageId") String languageId,
                         @Param(value = "companyId") String companyId,
                         @Param(value = "partnerId") String partnerId,
                         @Param(value = "masterAirwayBill") String masterAirwayBill,
                         @Param(value = "houseAirwayBill") String houseAirwayBill);

    @Query(value = "Select \n" +
            "TO_CURRENCY_VALUE currencyValue, \n" +
            "TO_CURRENCY_ID currencyId \n " +
            "From tblcurrencyexchangerate  \n" +
            "Where \n" +
            "C_ID IN (:companyId) and \n" +
            "FROM_CURRENCY_ID IN (:freightCurrency) and \n" +
            "is_deleted = 0", nativeQuery = true)
    IKeyValuePair getToCurrencyValue(@Param(value = "companyId") String companyId,
                                     @Param(value = "freightCurrency") String freightCurrency);

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndBondedIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String bondedId, Long deletionIndicator);

    // Get All NonDeleted BondedManifests
    @Query(value = "Select * From tblbondedmanifest h \n" +
            "Where h.IS_DELETED = 0", nativeQuery = true)
    List<ReplicaBondedManifest> getAllNonDeletedBondedManifests();


    // Find BondedManifests with given Params Only
    @Query(value = "SELECT * FROM tblbondedmanifest tm \n" +
            "WHERE tm.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tm.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tm.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tm.PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:masterAirwayBill, NULL) IS NULL OR tm.MASTER_AIRWAY_BILL IN (:masterAirwayBill)) \n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tm.HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) \n" +
            "AND (COALESCE(:hsCode, NULL) IS NULL OR tm.HS_CODE IN (:hsCode)) \n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR tm.PIECE_ID IN (:pieceId)) \n" +
            "AND (COALESCE(:pieceItemId, NULL) IS NULL OR tm.PIECE_ITEM_ID IN (:pieceItemId)) \n" +
            "AND (COALESCE(:bondedId, NULL) IS NULL OR tm.BONDED_ID IN (:bondedId))", nativeQuery = true)
    List<ReplicaBondedManifest> findBondedManifestsWithQry(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("masterAirwayBill") List<String> masterAirwayBill,
            @Param("houseAirwayBill") List<String> houseAirwayBill,
            @Param("bondedId") List<String> bondedId,
            @Param("hsCode") List<String> hsCode,
            @Param("pieceId") List<String> pieceId,
            @Param("pieceItemId") List<String> pieceItemId);


    @Query(value = "select " +
            "(select load_type_text from tblloadtype where c_id = :companyId and lang_id = :languageId and load_type_id = :loadTypeId and is_deleted = 0) as loadTypeText, " +
            "(select service_type_text from tblservicetype where c_id = :companyId and lang_id = :languageId and service_type_id = :serviceTypeId and is_deleted = 0) as serviceTypeText",
            nativeQuery = true)
    IKeyValuePair getStatusServiceType(@Param("languageId") String languageId,
                                       @Param("companyId") String companyId,
                                       @Param("loadTypeId") String loadTypeId,
                                       @Param("serviceTypeId") String serviceTypeId);

    @Query(value = "select load_type_text as loadTypeText from tblloadtype where c_id = :companyId and lang_id = :languageId " +
            "and load_type_id = :loadTypeId and is_deleted = 0", nativeQuery = true)
    String getLoadTypeText(@Param("languageId") String languageId,
                           @Param("companyId") String companyId,
                           @Param("loadTypeId") String loadTypeId);

    @Query(value = "select service_type_text as serviceTypeText from tblservicetype where c_id = :companyId and lang_id = :languageId " +
            "and service_type_id = :serviceTypeId and is_deleted = 0", nativeQuery = true)
    String getServiceTypeText(@Param("languageId") String languageId,
                              @Param("companyId") String companyId,
                              @Param("serviceTypeId") String serviceTypeId);


    @Query(value = "Select * From tblconsignment_entity\n" +
            "Where IS_DELETED=0 and\n" +
            "(COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) and \n" +
            "(COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) and \n" +
            "(COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId) and \n" +
            "(COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR PARTNER_HOUSE_AB = :partnerHouseAirwayBill) and \n" +
            "(COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR PARTNER_MASTER_AB = :partnerMasterAirwayBill)", nativeQuery = true)
    Optional<ReplicaConsignmentEntity> getConsignmentValues(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "partnerId") String partnerId,
            @Param(value = "partnerMasterAirwayBill") String partnerMasterAirwayBill,
            @Param(value = "partnerHouseAirwayBill") String partnerHouseAirwayBill);

    @Query(value = "Select \n" +
            "AIRPORT_DESTINATION_CODE airportDestinationCode \n" +
            "From tblconsignment_entity  \n" +
            "Where \n" +
            "C_ID IN (:companyId) and \n" +
            "LANG_ID IN (:languageId) and \n"+
            "PARTNER_ID IN (:partnerId) and \n"+
            "PARTNER_MASTER_AB IN (:partnerMasterAirwayBill) and \n"+
            "PARTNER_HOUSE_AB IN (:partnerHouseAirwayBill) and \n"+
            "is_deleted = 0", nativeQuery = true)
    String getFinalDestination(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "partnerId") String partnerId,
            @Param(value = "partnerMasterAirwayBill") String partnerMasterAirwayBill,
            @Param(value = "partnerHouseAirwayBill") String partnerHouseAirwayBill);


}