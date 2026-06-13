package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.inventorytable.FindInventoryTable;
import com.courier.overc360.api.midmile.replica.model.inventorytable.ReplicaInventoryTable;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaInventoryTableSpecification implements Specification<ReplicaInventoryTable> {

    FindInventoryTable findInventoryTable;

    public ReplicaInventoryTableSpecification(FindInventoryTable inputSearchParams) {
        this.findInventoryTable = inputSearchParams;
    }

    public Predicate toPredicate(Root<ReplicaInventoryTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (findInventoryTable.getLanguageId() != null && !findInventoryTable.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findInventoryTable.getLanguageId()));
        }
        if (findInventoryTable.getCompanyId() != null && !findInventoryTable.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findInventoryTable.getCompanyId()));
        }
        if (findInventoryTable.getCustomerId() != null && !findInventoryTable.getCustomerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("customerId");
            predicates.add(group.in(findInventoryTable.getCustomerId()));
        }
        if (findInventoryTable.getHouseAirwayBill() != null && !findInventoryTable.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findInventoryTable.getHouseAirwayBill()));
        }
        if (findInventoryTable.getHubCode() != null && !findInventoryTable.getHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hubCode");
            predicates.add(group.in(findInventoryTable.getHubCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
    }
}
