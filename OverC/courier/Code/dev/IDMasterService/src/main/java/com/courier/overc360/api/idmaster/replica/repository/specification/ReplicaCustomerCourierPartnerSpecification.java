package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.FindCustomerCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.ReplicaCustomerCourierPartner;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ReplicaCustomerCourierPartnerSpecification implements Specification<ReplicaCustomerCourierPartner> {

    FindCustomerCourierPartner findCustomerCourierPartner;

    public ReplicaCustomerCourierPartnerSpecification(FindCustomerCourierPartner inputSearchParams) {
        this.findCustomerCourierPartner = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCustomerCourierPartner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCustomerCourierPartner.getLanguageId() != null && !findCustomerCourierPartner.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCustomerCourierPartner.getLanguageId()));
        }
        if (findCustomerCourierPartner.getCompanyId() != null && !findCustomerCourierPartner.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCustomerCourierPartner.getCompanyId()));
        }
        if (findCustomerCourierPartner.getCourierPartnerId() != null && !findCustomerCourierPartner.getCourierPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("courierPartnerId");
            predicates.add(group.in(findCustomerCourierPartner.getCourierPartnerId()));
        }
        if (findCustomerCourierPartner.getStatusId() != null && !findCustomerCourierPartner.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCustomerCourierPartner.getStatusId()));
        }
        if (findCustomerCourierPartner.getPartnerId() != null && !findCustomerCourierPartner.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findCustomerCourierPartner.getPartnerId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

