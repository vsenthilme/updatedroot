package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.language.FindLanguage;
import com.courier.overc360.api.idmaster.replica.model.language.ReplicaLanguage;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaLanguageSpecification implements Specification<ReplicaLanguage> {

    FindLanguage findLanguage;

    public ReplicaLanguageSpecification(FindLanguage inputSearchParams) {
        this.findLanguage = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaLanguage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findLanguage.getLanguageId() != null && !findLanguage.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findLanguage.getLanguageId()));
        }
//        if (findLanguage.getFromDate() != null && findLanguage.getToDate() != null) {
//            predicates.add(cb.between(root.get("createdOn"), findLanguage.getFromDate(), findLanguage.getToDate()));
//        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
