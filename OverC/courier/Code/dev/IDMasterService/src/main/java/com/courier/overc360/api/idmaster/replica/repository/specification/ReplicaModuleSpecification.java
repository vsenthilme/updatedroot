package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.module.FindModule;
import com.courier.overc360.api.idmaster.replica.model.module.ReplicaModule;
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
public class ReplicaModuleSpecification implements Specification<ReplicaModule> {

    FindModule findModule;

    public ReplicaModuleSpecification(FindModule inputSearchParams) {
        this.findModule = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaModule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findModule.getCompanyId() != null && !findModule.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findModule.getCompanyId()));
        }
        if (findModule.getModuleId() != null && !findModule.getModuleId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("moduleId");
            predicates.add(group.in(findModule.getModuleId()));
        }
        if (findModule.getLanguageId() != null && !findModule.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findModule.getLanguageId()));
        }
        if (findModule.getMenuId() != null && !findModule.getMenuId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("menuId");
            predicates.add(group.in(findModule.getMenuId()));
        }
        if (findModule.getSubMenuId() != null && !findModule.getSubMenuId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subMenuId");
            predicates.add(group.in(findModule.getSubMenuId()));
        }
        if (findModule.getStatusId() != null && !findModule.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findModule.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
