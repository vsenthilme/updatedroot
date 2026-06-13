package com.tekclover.wms.api.mfg.repository.specification;
import com.tekclover.wms.api.mfg.model.sorting.SearchSorting;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SortingSpecification implements Specification<Sorting> {

    SearchSorting searchSorting;

    public SortingSpecification(SearchSorting inputSearchParams) {
        this.searchSorting = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<Sorting> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchSorting.getCompanyCodeId() != null && !searchSorting.getCompanyCodeId().isEmpty()) {
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchSorting.getCompanyCodeId()));
        }

        if (searchSorting.getPlantId() != null && !searchSorting.getPlantId().isEmpty()) {
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchSorting.getPlantId()));
        }

        if (searchSorting.getLanguageId() != null && !searchSorting.getLanguageId().isEmpty()) {
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchSorting.getLanguageId()));
        }

        if (searchSorting.getWarehouseId() != null && !searchSorting.getWarehouseId().isEmpty()) {
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchSorting.getWarehouseId()));
        }

        if (searchSorting.getProductionOrderNo() != null && !searchSorting.getProductionOrderNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchSorting.getProductionOrderNo()));
        }

        if (searchSorting.getProductionOrderLineNo()!= null && !searchSorting.getProductionOrderLineNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchSorting.getProductionOrderLineNo()));
        }

        if (searchSorting.getReceipeId() != null && !searchSorting.getReceipeId().isEmpty()) {
            final Path<Group> group = root.get("receipeId");
            predicates.add(group.in(searchSorting.getReceipeId()));
        }

        if (searchSorting.getOperationNumber() != null && !searchSorting.getOperationNumber().isEmpty()) {
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchSorting.getOperationNumber()));
        }

        if (searchSorting.getItemCode() != null && !searchSorting.getItemCode().isEmpty()) {
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchSorting.getItemCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}