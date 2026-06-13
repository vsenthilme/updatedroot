package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.logicmaster.FindLogicMaster;
import com.courier.overc360.api.idmaster.replica.model.logicmaster.ReplicaLogicMaster;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class ReplicaLogicMasterSpecification implements Specification<ReplicaLogicMaster> {

    FindLogicMaster findLogicMaster;

    public ReplicaLogicMasterSpecification(FindLogicMaster inputSearchParams) {
        this.findLogicMaster = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaLogicMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findLogicMaster.getLanguageId() != null && !findLogicMaster.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findLogicMaster.getLanguageId()));
        }
        if (findLogicMaster.getCompanyId() != null && !findLogicMaster.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findLogicMaster.getCompanyId()));
        }
        if (findLogicMaster.getConsoleCountId() != null && !findLogicMaster.getConsoleCountId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consoleCountId");
            predicates.add(group.in(findLogicMaster.getConsoleCountId()));
        }
        if (findLogicMaster.getStatusId() != null && !findLogicMaster.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findLogicMaster.getStatusId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}


