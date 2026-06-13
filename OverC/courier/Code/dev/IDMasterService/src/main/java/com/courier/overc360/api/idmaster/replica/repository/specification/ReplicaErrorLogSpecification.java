package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.errorlog.FindErrorLog;
import com.courier.overc360.api.idmaster.replica.model.errorlog.ReplicaErrorLog;
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
public class ReplicaErrorLogSpecification implements Specification<ReplicaErrorLog> {

    FindErrorLog findErrorLog;

    public ReplicaErrorLogSpecification(FindErrorLog inputSearchParams) {
        this.findErrorLog = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findErrorLog.getLanguageId() != null && !findErrorLog.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findErrorLog.getLanguageId()));
        }
        if (findErrorLog.getCompanyId() != null && !findErrorLog.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findErrorLog.getCompanyId()));
        }
        if (findErrorLog.getRefDocNumber() != null && !findErrorLog.getRefDocNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("refDocNumber");
            predicates.add(group.in(findErrorLog.getRefDocNumber()));
        }
        if (findErrorLog.getFromLogDate() != null && findErrorLog.getToLogDate() != null) {
            predicates.add(cb.between(root.get("logDate"), findErrorLog.getFromLogDate(),
                    findErrorLog.getToLogDate()));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
