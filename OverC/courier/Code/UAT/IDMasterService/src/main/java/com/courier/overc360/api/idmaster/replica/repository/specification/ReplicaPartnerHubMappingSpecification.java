package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.FindPartnerHubMapping;
import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.ReplicaPartnerHubMapping;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaPartnerHubMappingSpecification implements Specification<ReplicaPartnerHubMapping> {

    FindPartnerHubMapping findPartnerHubMapping;

    public ReplicaPartnerHubMappingSpecification(FindPartnerHubMapping inputSearchParams) {
        this.findPartnerHubMapping = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaPartnerHubMapping> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findPartnerHubMapping.getLanguageId() != null && !findPartnerHubMapping.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findPartnerHubMapping.getLanguageId()));
        }
        if (findPartnerHubMapping.getCompanyId() != null && !findPartnerHubMapping.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findPartnerHubMapping.getCompanyId()));
        }
        if (findPartnerHubMapping.getHubCode() != null && !findPartnerHubMapping.getHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hubCode");
            predicates.add(group.in(findPartnerHubMapping.getHubCode()));
        }
        if (findPartnerHubMapping.getPartnerType() != null && !findPartnerHubMapping.getPartnerType().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerType");
            predicates.add(group.in(findPartnerHubMapping.getPartnerType()));
        }
        if (findPartnerHubMapping.getPartnerId() != null && !findPartnerHubMapping.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findPartnerHubMapping.getPartnerId()));
        }
        if (findPartnerHubMapping.getStatusId() != null && !findPartnerHubMapping.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findPartnerHubMapping.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
