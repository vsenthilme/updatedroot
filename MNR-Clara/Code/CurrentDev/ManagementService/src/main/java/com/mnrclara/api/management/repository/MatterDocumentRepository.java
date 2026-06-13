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

import com.mnrclara.api.management.model.matterdocument.MatterDocument;

@Repository
@Transactional
public interface MatterDocumentRepository extends JpaRepository<MatterDocument,Long>,JpaSpecificationExecutor<MatterDocument> {

	public Optional<MatterDocument> findByDocumentNo(String mattertDocumentId);
	public Optional<MatterDocument> findByMatterDocumentId(Long matterDocumentId);
	
	public List<MatterDocument> findByLanguageIdAndClassIdAndClientIdAndMatterNumberAndDocumentNo (
			String languageId,
			Long classId,
			String clientId,
			String matterNumber,
			String documentNo);
	
	public List<MatterDocument> findTopByLanguageIdAndClassIdAndMatterNumberAndClientIdAndDocumentNoOrderByDocumentUrlVersionDesc (
			String languageId,
			Long classId,
			String matterNumber,
			String clientId,
			String documentNo);

	@Query(value = "select matter_text as matterText from tblmattergenaccid \n" +
			" WHERE matter_no = :matterNo AND is_deleted = 0 ", nativeQuery = true)
	String findByMatterText(@Param("matterNo") String matterNo);

	@Modifying
	@Query(value = "update tblmatterdocumentid set noti_status = 1 where MATTER_NO = :matterNo \n" +
			"and class_id = :classId and client_id = :clientId and is_deleted = 0", nativeQuery = true)
	public void updateNotificationStatus(@Param("matterNo") String matterNo,
										 @Param("classId") Long classId ,
										 @Param("clientId") String clientId);

	@Query(value = "select MATTER_NO as matterNo, class_id as classId, client_id as clientId from tblmatterdocumentid \n" +
			" WHERE noti_status = 0 AND is_deleted = 0 ", nativeQuery = true)
	List<IKeyValuePair> findByMatterDocIdAndClassIdAndClientId();

}