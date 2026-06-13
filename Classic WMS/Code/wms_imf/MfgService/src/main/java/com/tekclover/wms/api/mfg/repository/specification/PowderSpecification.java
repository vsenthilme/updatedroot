package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.powder.SearchPowder;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PowderSpecification implements Specification<Powder> {

    SearchPowder searchPowder;

    public PowderSpecification(SearchPowder inputSearchParams) {
        this.searchPowder = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<Powder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchPowder.getCompanyCodeId() != null && !searchPowder.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchPowder.getCompanyCodeId()));
        }

        if (searchPowder.getPlantId() != null && !searchPowder.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchPowder.getPlantId()));
        }

        if (searchPowder.getLanguageId() != null && !searchPowder.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchPowder.getLanguageId()));
        }

        if (searchPowder.getWarehouseId() != null && !searchPowder.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchPowder.getWarehouseId()));
        }

        if (searchPowder.getOperationNumber() != null && !searchPowder.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchPowder.getOperationNumber()));
        }

        if (searchPowder.getReceipeId() != null && !searchPowder.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchPowder.getReceipeId()));
        }

        if (searchPowder.getProductionOrderNo() != null && !searchPowder.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchPowder.getProductionOrderNo()));
        }

        if (searchPowder.getProductionOrderLineNo() != null && !searchPowder.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchPowder.getProductionOrderLineNo()));
        }

        if (searchPowder.getItemCode() != null && !searchPowder.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchPowder.getItemCode()));
        }

        if (searchPowder.getStatusId() != null && !searchPowder.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchPowder.getStatusId()));
        }

        if (searchPowder.getStartCreatedOn()!= null && searchPowder.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchPowder.getStartCreatedOn(),
                    searchPowder.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}