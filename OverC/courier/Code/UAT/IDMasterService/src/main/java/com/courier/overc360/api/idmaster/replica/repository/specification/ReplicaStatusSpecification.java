package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.status.FindReplicaStatus;
import com.courier.overc360.api.idmaster.replica.model.status.ReplicaStatus;
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
public class ReplicaStatusSpecification implements Specification<ReplicaStatus> {

    FindReplicaStatus findReplicaStatus;

    public ReplicaStatusSpecification(FindReplicaStatus inputSearchParams) {
        this.findReplicaStatus = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaStatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findReplicaStatus.getLanguageId() != null && !findReplicaStatus.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findReplicaStatus.getLanguageId()));
        }
        if (findReplicaStatus.getStatusId() != null && !findReplicaStatus.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findReplicaStatus.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
