package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.menu.FindMenu;
import com.courier.overc360.api.idmaster.replica.model.menu.ReplicaMenu;
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
public class ReplicaMenuSpecification implements Specification<ReplicaMenu> {

    FindMenu findMenu;

    public ReplicaMenuSpecification(FindMenu inputSearchParams) {
        this.findMenu = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findMenu.getLanguageId() != null && !findMenu.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findMenu.getLanguageId()));
        }
        if (findMenu.getCompanyId() != null && !findMenu.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findMenu.getCompanyId()));
        }
        if (findMenu.getMenuId() != null && !findMenu.getMenuId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("menuId");
            predicates.add(group.in(findMenu.getMenuId()));
        }
        if (findMenu.getSubMenuId() != null && !findMenu.getSubMenuId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subMenuId");
            predicates.add(group.in(findMenu.getSubMenuId()));
        }
        if (findMenu.getAuthorizationObjectId() != null && !findMenu.getAuthorizationObjectId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("authorizationObjectId");
            predicates.add(group.in(findMenu.getAuthorizationObjectId()));
        }
        if (findMenu.getStatusId() != null && !findMenu.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findMenu.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
