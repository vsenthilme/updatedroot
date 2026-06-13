package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.specialapproval.FindSpecialApproval;
import com.courier.overc360.api.idmaster.replica.model.specialapproval.ReplicaSpecialApproval;
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
public class ReplicaSpecialApprovalSpecification implements Specification<ReplicaSpecialApproval> {

    FindSpecialApproval findSpecialApproval;

    public ReplicaSpecialApprovalSpecification(FindSpecialApproval inputSearchParams) {
        this.findSpecialApproval = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaSpecialApproval> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findSpecialApproval.getSpecialApprovalId() != null && !findSpecialApproval.getSpecialApprovalId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("specialApprovalId");
            predicates.add(group.in(findSpecialApproval.getSpecialApprovalId()));
        }
        if (findSpecialApproval.getLanguageId() != null && !findSpecialApproval.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findSpecialApproval.getLanguageId()));
        }
        if (findSpecialApproval.getCompanyId() != null && !findSpecialApproval.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findSpecialApproval.getCompanyId()));
        }
        if (findSpecialApproval.getStatusId() != null && !findSpecialApproval.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findSpecialApproval.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
