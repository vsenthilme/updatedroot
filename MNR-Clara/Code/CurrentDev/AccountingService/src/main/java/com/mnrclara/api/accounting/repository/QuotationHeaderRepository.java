package com.mnrclara.api.accounting.repository;

import com.mnrclara.api.accounting.model.IkeyValuePair;
import com.mnrclara.api.accounting.model.impl.QuotationHeaderImpl;
import com.mnrclara.api.accounting.model.quotation.QuotationHeaderEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface QuotationHeaderRepository extends PagingAndSortingRepository<QuotationHeaderEntity,Long>, JpaSpecificationExecutor<QuotationHeaderEntity> {
	public List<QuotationHeaderEntity> findAll();
	public Optional<QuotationHeaderEntity>
		findByQuotationNoAndQuotationRevisionNoAndDeletionIndicator(String quotationNo,
																	Long quotationRevisionNo,
																	Long deletionIndicator);
	public Optional<QuotationHeaderEntity> findByQuotationNoAndDeletionIndicator(String quotationNo, Long deletionIndicator);

	public Optional<QuotationHeaderEntity> findByMatterNumber(String matterNumber);


	@Query(value = "select token_id as tokenId from tblhhtnotification where class_id = :classId and \n" +
			" client_id = :clientId and is_deleted = 0 ", nativeQuery = true)
	public List<String> getDeviceToken(@Param("classId")Long classId,
									   @Param("clientId")String clientId);

	@Query(value = "select quote_no as quotationNo , class_id as classId, client_id as clientId from tblquotationheader \n" +
			" WHERE noti_status = 0 AND is_deleted = 0 ", nativeQuery = true)
	List<IkeyValuePair> findByQuotationNoAndClassIdAndClientId();

	@Modifying
	@Query(value = "update tblquotationheader set noti_status = 1 where quote_no = :quotationNo \n" +
			"and class_id = :classId and client_id = :clientId and is_deleted = 0", nativeQuery = true)
	public void updateNotificationStatus(@Param("quotationNo") String quotationNo,
										 @Param("classId") Long classId ,
										 @Param("clientId") String clientId);

//	@Query(value = "select noti_status as notificationStatus from tblquotationheader where quote_no = :quotationNo \n " +
//			"and class_id = :classId and client_id = :clientId and REF_FIELD_8 = Sent and is_deleted = 0", nativeQuery = true)
//	public List<Long> notificationStatus(@Param("quotationNo") String quotationNo,
//										 @Param("classId") Long classId,
//										 @Param("clientId") String clientId);

	@Query(value = "SELECT noti_status AS notificationStatus " +
			"FROM tblquotationheader " +
			"WHERE quote_no = :quotationNo " +
			"AND class_id = :classId " +
			"AND client_id = :clientId " +
			"AND REF_FIELD_8 = 'Sent' " +  // Enclose 'Sent' in single quotes
			"AND is_deleted = 0", nativeQuery = true)
	public List<Long> notificationStatus(@Param("quotationNo") String quotationNo,
										 @Param("classId") Long classId,
										 @Param("clientId") String clientId);

    @Query(value = "Select \n"
            + "QUOTE_NO quotationNo, \n"
            + "QUOTE_REV_NO quotationRevisionNo, \n"
            + "LANG_ID languageId, \n"
            + "CLASS_ID classId, \n"
            + "MATTER_NO matterNumber, \n"
            + "CLIENT_ID clientId, \n"
            + "CASE_CATEGORY_ID caseCategoryId, \n"
            + "CASE_SUB_CATEGORY_ID caseSubCategoryId, \n"
            + "FIRST_LAST_NM firstNameLastName, \n"
            + "CORPORATION corporation, \n"
            + "QUOTE_DATE quotationDate, \n"
            + "QUOTE_AMT quotationAmount, \n"
            + "CURRENCY currency, \n"
            + "DUE_DATE dueDate, \n"
            + "PAYMENT_PLAN_NO paymentPlanNumber, \n"
            + "TERM_TEXT termDetails, \n"
            + "SENT_ON sentDate, \n"
            + "APPROVED_ON approvedDate, \n"
            + "STATUS_ID statusId, \n"
            + "IS_DELETED deletionIndicator, \n"
            + "REF_FIELD_1 referenceField1, \n"
            + "REF_FIELD_2 referenceField2, \n"
            + "REF_FIELD_3 referenceField3, \n"
            + "REF_FIELD_4 referenceField4, \n"
            + "REF_FIELD_5 referenceField5, \n"
            + "REF_FIELD_6 referenceField6, \n"
            + "REF_FIELD_7 referenceField7, \n"
            + "REF_FIELD_8 referenceField8, \n"
            + "REF_FIELD_9 referenceField9, \n"
            + "REF_FIELD_10 referenceField10, \n"
            + "CTD_BY createdBy, \n"
            + "CTD_ON createdOn, \n"
            + "UTD_BY updatedBy, \n"
            + "UTD_ON updatedOn, \n"
            + "NOTI_STATUS notificationStatus \n"
            + "from tblquotationheader pph \n"
            + "where \n"
            + "is_deleted = 0 and \n"
            + "QUOTE_REV_NO in  \n"
            + "(select max(QUOTE_REV_NO) from tblquotationheader pph2 where pph2.QUOTE_NO = pph.QUOTE_NO) and \n"
            + "(COALESCE(:clientId) IS NULL OR (pph.CLIENT_ID IN (:clientId))) and \n"
            + "(COALESCE(:matterNumber) IS NULL OR (pph.MATTER_NO IN (:matterNumber))) and \n"
            + "(COALESCE(:quotationNo) IS NULL OR (pph.QUOTE_NO IN (:quotationNo))) and \n"
            + "(COALESCE(:firstNameLastName) IS NULL OR (pph.FIRST_LAST_NM IN (:firstNameLastName))) and \n"
            + "(COALESCE(:statusId) IS NULL OR (pph.STATUS_ID IN (:statusId))) and \n"
            + "(COALESCE(:caseCategoryId) IS NULL OR (pph.CASE_SUB_CATEGORY_ID IN (:caseCategoryId))) and \n"
            + "(COALESCE(:createdBy) IS NULL OR (pph.CTD_BY IN (:createdBy))) and  \n"
            + "(COALESCE(:startQuotationDate) IS NULL OR (pph.QUOTE_DATE BETWEEN :startQuotationDate AND :endQuotationDate)) ", nativeQuery = true)
    public List<QuotationHeaderImpl> findQuotationHeader(@Param("clientId") List<String> clientId,
                                                         @Param("matterNumber") List<String> matterNumber,
                                                         @Param("quotationNo") List<String> quotationNo,
                                                         @Param("firstNameLastName") List<String> firstNameLastName,
                                                         @Param("createdBy") List<String> createdBy,
                                                         @Param("statusId") List<Long> statusId,
                                                         @Param("caseCategoryId") List<Long> caseCategoryId,
                                                         @Param("startQuotationDate") Date startQuotationDate,
                                                         @Param("endQuotationDate") Date endQuotationDate);

}