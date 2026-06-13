package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.event.FindEvent;
import com.courier.overc360.api.idmaster.replica.model.event.ReplicaEvent;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaEventSpecification implements Specification<ReplicaEvent> {

    FindEvent findEvent;
    public ReplicaEventSpecification(FindEvent inputSearchParams) {
        this.findEvent = inputSearchParams;
    }
    @Override
    public Predicate toPredicate(Root<ReplicaEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (findEvent.getEventCode() != null && !findEvent.getEventCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("eventCode");
            predicates.add(group.in(findEvent.getEventCode()));
        }
        if (findEvent.getLanguageId() != null && !findEvent.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findEvent.getLanguageId()));
        }
        if (findEvent.getCompanyId() != null && !findEvent.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findEvent.getCompanyId()));
        }
        if (findEvent.getStatusCode() != null && !findEvent.getStatusCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusCode");
            predicates.add(group.in(findEvent.getStatusCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }

}
