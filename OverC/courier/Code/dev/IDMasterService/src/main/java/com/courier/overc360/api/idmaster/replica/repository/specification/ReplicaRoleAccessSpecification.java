package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.roleaccess.FindRoleAccess;
import com.courier.overc360.api.idmaster.replica.model.roleaccess.ReplicaRoleAccess;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReplicaRoleAccessSpecification implements Specification<ReplicaRoleAccess> {

    @Autowired
    FindRoleAccess findRoleAccess;

    public ReplicaRoleAccessSpecification(FindRoleAccess inputSearchParams) {
        this.findRoleAccess = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRoleAccess> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRoleAccess.getCompanyId() != null && !findRoleAccess.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRoleAccess.getCompanyId()));
        }
        if (findRoleAccess.getRoleId() != null && !findRoleAccess.getRoleId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("roleId");
            predicates.add(group.in(findRoleAccess.getRoleId()));
        }
        if (findRoleAccess.getMenuId() != null && !findRoleAccess.getMenuId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("menuId");
            predicates.add(group.in(findRoleAccess.getMenuId()));
        }
        if (findRoleAccess.getSubMenuId() != null && !findRoleAccess.getSubMenuId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subMenuId");
            predicates.add(group.in(findRoleAccess.getSubMenuId()));
        }
        if (findRoleAccess.getLanguageId() != null && !findRoleAccess.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRoleAccess.getLanguageId()));
        }
        if (findRoleAccess.getStatusId() != null && !findRoleAccess.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findRoleAccess.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

