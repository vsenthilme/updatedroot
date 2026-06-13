package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.customcharges.FindCustomCharge;
import com.courier.overc360.api.idmaster.replica.model.customcharges.ReplicaCustomCharges;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ReplicaCustomChargeSpecification implements Specification<ReplicaCustomCharges> {

    FindCustomCharge findCustomCharge;

    public ReplicaCustomChargeSpecification(FindCustomCharge inputSearchParams) {
        this.findCustomCharge = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCustomCharges> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCustomCharge.getLanguageId() != null && !findCustomCharge.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCustomCharge.getLanguageId()));
        }
        if (findCustomCharge.getCompanyId() != null && !findCustomCharge.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCustomCharge.getCompanyId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
