package com.mnrclara.api.crm.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mnrclara.api.crm.model.potentialclient.PotentialClientImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnrclara.api.crm.model.potentialclient.PotentialClient;

@Repository
@Transactional
public interface PotentialClientRepository extends JpaRepository<PotentialClient, Long>, JpaSpecificationExecutor<PotentialClient> {
	
	public Optional<PotentialClient> findByPotentialClientId(@Param("potentialClientId") String potentialClientId);
	public List<PotentialClient> findByClassId(Long classId);
	
	@Query (value = "SELECT * FROM MNRCLARA.tblpotentialclientid WHERE \r\n"
			+ "MATCH (FIRST_NM, LAST_NM, FIRST_LAST_NM, LAST_FIRST_NM, EMAIL_ID, \r\n"
			+ "ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, \r\n"
			+ "STATE, CITY, COUNTRY, MAIL_ADDRESS, OCCUPATION)\r\n"
			+ "AGAINST (:searchText)", nativeQuery = true)
	List<PotentialClient> findRecords(@Param(value = "searchText") String searchText);
	
	public List<PotentialClient> findByInquiryNumberAndDeletionIndicator(String inquiryNumber, Long deletionIndicator);

	@Query(value =
			"SELECT cg.CTD_ON AS clientCreatedDate " +
					"FROM tblclientgeneralid cg " +
					"WHERE " +
					"(cg.CTD_ON BETWEEN :startClientCreateDate AND :endClientCreateDate) AND " +
					"(:potentialClientId IS NULL OR cg.POT_CLIENT_ID = :potentialClientId) " +
					" LIMIT 1",
			nativeQuery = true)
	Date getClientCreatedDates(
			@Param("startClientCreateDate") Date startClientCreateDate,
			@Param("endClientCreateDate") Date endClientCreateDate,
			@Param("potentialClientId") String potentialClientId);

	@Query(value =
			"SELECT cg.CTD_ON AS clientCreatedDate " +
					"FROM tblclientgeneralid cg " +
					"WHERE " +
					"(:potentialClientId IS NULL OR cg.POT_CLIENT_ID = :potentialClientId) " +
					" LIMIT 1 ",
			nativeQuery = true)
	Date getClientCreatedDates(
			@Param("potentialClientId") String potentialClientId);

	@Query(value = "SELECT cg.CTD_ON AS clientCreatedDate, DATE_FORMAT(cg.CTD_ON, '%m-%d-%Y') dateString " +
					"FROM tblclientgeneralid cg WHERE cg.is_deleted = 0 AND " +
					"(:potentialClientId IS NULL OR cg.POT_CLIENT_ID = :potentialClientId) " +
					" LIMIT 1 ",nativeQuery = true)
	PotentialClientImpl getClientCreatedDate(@Param("potentialClientId") String potentialClientId);

	@Query(value = "SELECT cg.CTD_ON AS clientCreatedDate, DATE_FORMAT(cg.CTD_ON, '%m-%d-%Y') dateString " +
			"FROM tblclientgeneralid cg WHERE cg.is_deleted = 0 AND " +
			"(cg.CTD_ON BETWEEN :startClientCreateDate AND :endClientCreateDate) AND " +
			"(:potentialClientId IS NULL OR cg.POT_CLIENT_ID = :potentialClientId) " +
			" LIMIT 1", nativeQuery = true)
	PotentialClientImpl getClientCreatedDate(@Param("startClientCreateDate") Date startClientCreateDate,
											 @Param("endClientCreateDate") Date endClientCreateDate,
											 @Param("potentialClientId") String potentialClientId);


	@Query(value = "select status_text from tblstatusid where status_id = :statusId", nativeQuery = true)
	public String getStatusText(@Param("statusId") Long statusId);

	@Query(value = "select REF_FIELD_4 as referenceField4 from tblinquiryid where INQ_NO = :inquiryNumber", nativeQuery = true)
	public String getInquiryNumber(@Param("inquiryNumber") String inquiryNumber);
}
