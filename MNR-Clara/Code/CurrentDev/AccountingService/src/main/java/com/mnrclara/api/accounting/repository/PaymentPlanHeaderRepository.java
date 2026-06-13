package com.mnrclara.api.accounting.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mnrclara.api.accounting.model.IkeyValuePair;
import com.mnrclara.api.accounting.model.impl.PaymentPlanHeaderImpl;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnrclara.api.accounting.model.paymentplan.PaymentPlanHeaderEntity;

@Repository
@Transactional
public interface PaymentPlanHeaderRepository extends PagingAndSortingRepository<PaymentPlanHeaderEntity,Long>, 
													JpaSpecificationExecutor<PaymentPlanHeaderEntity> {
	
	public List<PaymentPlanHeaderEntity> findAll();
	public Optional<PaymentPlanHeaderEntity> 
		findByPaymentPlanNumberAndPaymentPlanRevisionNoAndDeletionIndicator(
				String paymentPlanNumber, Long paymentPlanRevisionNo, Long deletionIndicator);
	
	public PaymentPlanHeaderEntity findTopByPaymentPlanNumberOrderByPaymentPlanRevisionNoDesc (String paymentPlanNumber);


	@Query(value = "select payment_plan_no as paymentPlanNo, class_id as classId, client_id as clientId from tblpaymentplanheader \n" +
			" WHERE noti_status = 0 AND is_deleted = 0 ", nativeQuery = true)
	List<IkeyValuePair> findByPaymentPlanNoAndClassIdAndClientId();


	@Modifying
	@Query(value = "update tblpaymentplanheader set noti_status = 1 where payment_plan_no = :paymentPlanNo \n" +
			"and class_id = :classId and client_id = :clientId and is_deleted = 0", nativeQuery = true)
	public void updateNotificationStatus(@Param("paymentPlanNo") String paymentPlanNo,
										 @Param("classId") Long classId ,
										 @Param("clientId") String clientId);
@Query(value = "Select \n"
			+ "PAYMENT_PLAN_NO paymentPlanNumber, \n"
			+ "PLAN_REV_NO paymentPlanRevisionNo, \n"
			+ "LANG_ID languageId, \n"
			+ "CLASS_ID classId, \n"
			+ "MATTER_NO matterNumber, \n"
			+ "CLIENT_ID clientId, \n"
			+ "PAYMENT_PLAN_DATE paymentPlanDate, \n"
			+ "QUOTE_NO quotationNo, \n"
			+ "START_DATE paymentPlanStartDate, \n"
			+ "END_DATE endDate, \n"
			+ "NO_OF_INSTAL noOfInstallment, \n"
			+ "PAYMENT_PLAN_AMT paymentPlanTotalAmount, \n"
			+ "DUE_AMOUNT dueAmount, \n"
			+ "INSTALL_AMT installmentAmount, \n"
			+ "CURRENCY currency, \n"
			+ "PAYMENT_CAL_DAY paymentCalculationDayDate, \n"
			+ "PAY_PLAN_TEXT paymentPlanText, \n"
			+ "SENT_ON sentOn, \n"
			+ "APPROVED_ON approvedOn, \n"
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
			+ "UTD_ON updatedOn \n"
			+ "from tblpaymentplanheader pph \n"
			+ "where \n"
			+ "is_deleted = 0 and \n"
			+ "PLAN_REV_NO in  \n"
			+ "(select max(PLAN_REV_NO) from tblpaymentplanheader pph2 where pph2.PAYMENT_PLAN_NO = pph.PAYMENT_PLAN_NO) and \n"
			+ "(COALESCE(:clientId) IS NULL OR (pph.CLIENT_ID IN (:clientId))) and \n"
			+ "(COALESCE(:matterNumber) IS NULL OR (pph.MATTER_NO IN (:matterNumber))) and \n"
			+ "(COALESCE(:quotationNo) IS NULL OR (pph.QUOTE_NO IN (:quotationNo))) and \n"
			+ "(COALESCE(:paymentPlanNumber) IS NULL OR (pph.PAYMENT_PLAN_NO IN (:paymentPlanNumber))) and \n"
			+ "(COALESCE(:statusId) IS NULL OR (pph.STATUS_ID IN (:statusId))) and \n"
			+ "(COALESCE(:createdBy) IS NULL OR (pph.CTD_BY IN (:createdBy))) and  \n"
			+ "(COALESCE(:startPaymentPlanDate) IS NULL OR (pph.START_DATE BETWEEN :startPaymentPlanDate AND :endPaymentPlanDate)) and \n"
			+ "(COALESCE(:startCreatedOn) IS NULL OR (pph.CTD_ON BETWEEN :startCreatedOn AND :endCreatedOn)) ",nativeQuery = true)
			public List<PaymentPlanHeaderImpl> findPaymentPlanHeader(@Param("clientId") List<Long> clientId,
																	 @Param("matterNumber") List<String> matterNumber,
																	 @Param("quotationNo") List<String> quotationNo,
																	 @Param("paymentPlanNumber") List<String> paymentPlanNumber,
																	 @Param("createdBy") List<String> createdBy,
																	 @Param("statusId") List<Long> statusId,
																	 @Param("startPaymentPlanDate") Date startPaymentPlanDate,
																	 @Param("endPaymentPlanDate") Date endPaymentPlanDate,
																	 @Param("startCreatedOn") Date startCreatedOn,
																	 @Param("endCreatedOn") Date endCreatedOn);
}