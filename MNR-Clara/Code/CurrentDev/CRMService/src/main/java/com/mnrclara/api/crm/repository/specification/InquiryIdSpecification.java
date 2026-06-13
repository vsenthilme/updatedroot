package com.mnrclara.api.crm.repository.specification;

import com.mnrclara.api.crm.model.InquiryId.FindInquiryId;
import com.mnrclara.api.crm.model.InquiryId.InquiryId;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class InquiryIdSpecification implements Specification<InquiryId> {

    FindInquiryId findInquiryId;

    public InquiryIdSpecification(FindInquiryId inputSearchParams) {
        this.findInquiryId = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<InquiryId> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findInquiryId.getInquiryId() != null && !findInquiryId.getInquiryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("inquiryId");
            predicates.add(group.in(findInquiryId.getInquiryId()));
        }
        if (findInquiryId.getFileName() != null && !findInquiryId.getFileName().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("fileName");
            predicates.add(group.in(findInquiryId.getFileName()));
        }
        if (findInquiryId.getStatusId() != null && !findInquiryId.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findInquiryId.getStatusId()));
        }
        if (findInquiryId.getUploadedBy() != null && !findInquiryId.getUploadedBy().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("uploadedBy");
            predicates.add(group.in(findInquiryId.getUploadedBy()));
        }
        if (findInquiryId.getId() != null && !findInquiryId.getId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("id");
            predicates.add(group.in(findInquiryId.getId()));
        }
        if (findInquiryId.getStartDate() != null && findInquiryId.getEndDate() != null) {
            predicates.add(cb.between(root.get("uploadedOn"), findInquiryId.getStartDate(), findInquiryId.getEndDate()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}