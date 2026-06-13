package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.usertype.FindUserType;
import com.courier.overc360.api.idmaster.replica.model.usertype.ReplicaUserType;
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
public class ReplicaUserTypeSpecification implements Specification<ReplicaUserType> {

    FindUserType findUserType;

    public ReplicaUserTypeSpecification(FindUserType inputSearchParams) {
        this.findUserType = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaUserType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findUserType.getCompanyId() != null && !findUserType.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findUserType.getCompanyId()));
        }
        if (findUserType.getUserTypeId() != null && !findUserType.getUserTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("userTypeId");
            predicates.add(group.in(findUserType.getUserTypeId()));
        }
        if (findUserType.getLanguageId() != null && !findUserType.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findUserType.getLanguageId()));
        }
        if (findUserType.getStatusId() != null && !findUserType.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findUserType.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
