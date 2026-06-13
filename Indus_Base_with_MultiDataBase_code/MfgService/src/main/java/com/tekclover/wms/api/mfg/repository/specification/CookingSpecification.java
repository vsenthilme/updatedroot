package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.cooking.SearchCooking;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CookingSpecification implements Specification<Cooking> {

    SearchCooking searchCooking;

    public CookingSpecification(SearchCooking inputSearchParams) { this.searchCooking = inputSearchParams; }

    @Override
    public Predicate toPredicate(Root<Cooking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchCooking.getCompanyCodeId() != null && !searchCooking.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchCooking.getCompanyCodeId()));
        }

        if (searchCooking.getPlantId() != null && !searchCooking.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchCooking.getPlantId()));
        }

        if (searchCooking.getLanguageId() != null && !searchCooking.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchCooking.getLanguageId()));
        }

        if (searchCooking.getWarehouseId() != null && !searchCooking.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchCooking.getWarehouseId()));
        }

        if (searchCooking.getOperationNumber() != null && !searchCooking.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchCooking.getOperationNumber()));
        }

        if (searchCooking.getReceipeId() != null && !searchCooking.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchCooking.getReceipeId()));
        }

        if (searchCooking.getProductionOrderNo() != null && !searchCooking.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchCooking.getProductionOrderNo()));
        }

        if (searchCooking.getProductionOrderLineNo() != null && !searchCooking.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchCooking.getProductionOrderLineNo()));
        }

        if (searchCooking.getItemCode() != null && !searchCooking.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchCooking.getItemCode()));
        }

        if (searchCooking.getStatusId() != null && !searchCooking.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchCooking.getStatusId()));
        }

        if (searchCooking.getStartCreatedOn()!= null && searchCooking.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchCooking.getStartCreatedOn(),
                    searchCooking.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}