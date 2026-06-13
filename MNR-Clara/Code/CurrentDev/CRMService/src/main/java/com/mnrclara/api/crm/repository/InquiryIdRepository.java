package com.mnrclara.api.crm.repository;

import com.mnrclara.api.crm.model.InquiryId.InquiryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InquiryIdRepository extends JpaRepository<InquiryId,Long>, JpaSpecificationExecutor<InquiryId> {

    List<InquiryId> findByInquiryIdAndDeletionIndicator(Long inquiryId, Long deletionIndicator);
    InquiryId findByIdAndInquiryIdAndDeletionIndicator(Long id, Long inquiryId, Long deletionIndicator);
    InquiryId findByIdAndDeletionIndicator(Long id, Long deletionIndicator);

}