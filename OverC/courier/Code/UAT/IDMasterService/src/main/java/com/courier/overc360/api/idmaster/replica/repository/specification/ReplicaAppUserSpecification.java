package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.airportcode.FindAirportCode;
import com.courier.overc360.api.idmaster.replica.model.airportcode.ReplicaAirportCode;
import com.courier.overc360.api.idmaster.replica.model.appuser.FindAppUser;
import com.courier.overc360.api.idmaster.replica.model.appuser.ReplicaAppUser;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("serial")
public class ReplicaAppUserSpecification  implements Specification<ReplicaAppUser> {

    FindAppUser findAppUser;

    public ReplicaAppUserSpecification(FindAppUser inputSearchParams) {
        this.findAppUser = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaAppUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findAppUser.getAppUserId() != null && !findAppUser.getAppUserId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("appUserId");
            predicates.add(group.in(findAppUser.getAppUserId()));
        }
        if (findAppUser.getLanguageId() != null && !findAppUser.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findAppUser.getLanguageId()));
        }
        if (findAppUser.getCompanyId() != null && !findAppUser.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findAppUser.getCompanyId()));
        }
        if (findAppUser.getStatusId() != null && !findAppUser.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findAppUser.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
