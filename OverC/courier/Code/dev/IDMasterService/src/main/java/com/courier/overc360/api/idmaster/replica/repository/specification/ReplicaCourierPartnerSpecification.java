package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.courierpartner.FindCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.courierpartner.ReplicaCourierPartner;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")


public class ReplicaCourierPartnerSpecification implements Specification<ReplicaCourierPartner> {

    FindCourierPartner findCourierPartner;

    public ReplicaCourierPartnerSpecification(FindCourierPartner inputSearchParams) {
        this.findCourierPartner = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCourierPartner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCourierPartner.getLanguageId() != null && !findCourierPartner.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCourierPartner.getLanguageId()));
        }
        if (findCourierPartner.getCompanyId() != null && !findCourierPartner.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCourierPartner.getCompanyId()));
        }
        if (findCourierPartner.getCourierPartnerId() != null && !findCourierPartner.getCourierPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("courierPartnerId");
            predicates.add(group.in(findCourierPartner.getCourierPartnerId()));
        }
        if (findCourierPartner.getStatusId() != null && !findCourierPartner.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCourierPartner.getStatusId()));
        }
        if (findCourierPartner.getPartnerId() != null && !findCourierPartner.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findCourierPartner.getPartnerId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}


