package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.user.FindUserManagement;
import com.courier.overc360.api.idmaster.replica.model.user.ReplicaUserManagement;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaUserManagementSpecification implements Specification<ReplicaUserManagement> {

    FindUserManagement findUserManagement;

    public ReplicaUserManagementSpecification(FindUserManagement inputSearchParams) {
        this.findUserManagement = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaUserManagement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findUserManagement.getCompanyId() != null && !findUserManagement.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findUserManagement.getCompanyId()));
        }
        if (findUserManagement.getUserTypeId() != null && !findUserManagement.getUserTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("userTypeId");
            predicates.add(group.in(findUserManagement.getUserTypeId()));
        }
        if (findUserManagement.getLanguageId() != null && !findUserManagement.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findUserManagement.getLanguageId()));
        }
        if (findUserManagement.getUserId() != null && !findUserManagement.getUserId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("userId");
            predicates.add(group.in(findUserManagement.getUserId()));
        }
        if (findUserManagement.getUserRoleId() != null && !findUserManagement.getUserRoleId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("userRoleId");
            predicates.add(group.in(findUserManagement.getUserRoleId()));
        }
        if (findUserManagement.getHhtLoggedIn() != null) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hhtLoggedIn");
            predicates.add(group.in(findUserManagement.getHhtLoggedIn()));
        }
        if (findUserManagement.getPortalLoggedIn() != null) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("portalLoggedIn");
            predicates.add(group.in(findUserManagement.getPortalLoggedIn()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
