package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.diceslicechop.SearchDiceSliceChop;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DiceSliceChopSpecification implements Specification<DiceSliceChop> {

    SearchDiceSliceChop searchDiceSliceChop;

    public DiceSliceChopSpecification(SearchDiceSliceChop inputSearchParams) { this.searchDiceSliceChop = inputSearchParams; }

    @Override
    public Predicate toPredicate(Root<DiceSliceChop> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchDiceSliceChop.getCompanyCodeId() != null && !searchDiceSliceChop.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchDiceSliceChop.getCompanyCodeId()));
        }

        if (searchDiceSliceChop.getPlantId() != null && !searchDiceSliceChop.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchDiceSliceChop.getPlantId()));
        }

        if (searchDiceSliceChop.getLanguageId() != null && !searchDiceSliceChop.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchDiceSliceChop.getLanguageId()));
        }

        if (searchDiceSliceChop.getWarehouseId() != null && !searchDiceSliceChop.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchDiceSliceChop.getWarehouseId()));
        }

        if (searchDiceSliceChop.getOperationNumber() != null && !searchDiceSliceChop.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchDiceSliceChop.getOperationNumber()));
        }

        if (searchDiceSliceChop.getReceipeId() != null && !searchDiceSliceChop.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchDiceSliceChop.getReceipeId()));
        }

        if (searchDiceSliceChop.getProductionOrderNo() != null && !searchDiceSliceChop.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchDiceSliceChop.getProductionOrderNo()));
        }

        if (searchDiceSliceChop.getProductionOrderLineNo() != null && !searchDiceSliceChop.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchDiceSliceChop.getProductionOrderLineNo()));
        }

        if (searchDiceSliceChop.getItemCode() != null && !searchDiceSliceChop.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchDiceSliceChop.getItemCode()));
        }

        if (searchDiceSliceChop.getStatusId() != null && !searchDiceSliceChop.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchDiceSliceChop.getStatusId()));
        }

        if (searchDiceSliceChop.getStartCreatedOn()!= null && searchDiceSliceChop.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchDiceSliceChop.getStartCreatedOn(),
                    searchDiceSliceChop.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}