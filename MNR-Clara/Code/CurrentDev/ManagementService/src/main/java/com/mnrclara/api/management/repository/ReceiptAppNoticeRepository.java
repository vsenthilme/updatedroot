package com.mnrclara.api.management.repository;

import java.util.List;
import java.util.Optional;

import com.mnrclara.api.management.model.dto.IKeyValuePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnrclara.api.management.model.receiptappnotice.ReceiptAppNotice;


@Repository
@Transactional
public interface ReceiptAppNoticeRepository extends JpaRepository<ReceiptAppNotice,Long>, JpaSpecificationExecutor<ReceiptAppNotice> {
	
	public List<ReceiptAppNotice> findAll();
	public Optional<ReceiptAppNotice> findByLanguageIdAndClassIdAndMatterNumberAndReceiptNoAndDeletionIndicator(
			String languageId, Long classId, String matterNumber, String receiptNo, Long deletionIndicator );
	@Query(value = "select receipt_no as receiptNo, class_id as classId, client_id as clientId from tblreceiptappnotice \n" +
			" WHERE noti_status = 0 AND is_deleted = 0 ", nativeQuery = true)
	List<IKeyValuePair> findByReceiptNoAndClassIdAndClientId();

	@Modifying
	@Query(value = "update tblreceiptappnotice set noti_status = 1 where receipt_no = :receiptNo \n" +
			"and class_id = :classId and client_id = :clientId and is_deleted = 0", nativeQuery = true)
	public void updateNotificationStatus(@Param("receiptNo") String receiptNo,
										 @Param("classId") Long classId ,
										 @Param("clientId") String clientId);
}