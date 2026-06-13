package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.notification.FindNotification;
import com.courier.overc360.api.idmaster.replica.model.notification.ReplicaNotification;
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
public class ReplicaNotificationSpecification implements Specification<ReplicaNotification> {

    FindNotification findNotification;

    public ReplicaNotificationSpecification(FindNotification inputSearchParams) {
        this.findNotification = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findNotification.getLanguageId() != null && !findNotification.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findNotification.getLanguageId()));
        }
        if (findNotification.getCompanyId() != null && !findNotification.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findNotification.getCompanyId()));
        }
        if (findNotification.getNotificationId() != null && !findNotification.getNotificationId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("notificationId");
            predicates.add(group.in(findNotification.getNotificationId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
