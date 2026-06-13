package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.statusevent.FindStatusEvent;
import com.courier.overc360.api.idmaster.replica.model.statusevent.ReplicaStatusEvent;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaStatusEventSpecification implements Specification<ReplicaStatusEvent>{

    FindStatusEvent findStatusEvent;

    public ReplicaStatusEventSpecification(FindStatusEvent inputSearchParams) {
        this.findStatusEvent = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaStatusEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findStatusEvent.getLanguageId() != null && !findStatusEvent.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findStatusEvent.getLanguageId()));
        }
        if (findStatusEvent.getCompanyId() != null && !findStatusEvent.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findStatusEvent.getCompanyId()));
        }
        if (findStatusEvent.getTypeId() != null && !findStatusEvent.getTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("typeId");
            predicates.add(group.in(findStatusEvent.getTypeId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
