package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.fgDelivery.FgDelivery;
import com.tekclover.wms.api.mfg.model.fgDelivery.SearchFgDelivery;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class FgDeliverySpecification implements Specification<FgDelivery> {

    SearchFgDelivery searchFgDelivery;

    public FgDeliverySpecification(SearchFgDelivery inputSearchParams) { this.searchFgDelivery = inputSearchParams; }

    @Override
    public Predicate toPredicate(Root<FgDelivery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchFgDelivery.getCompanyCodeId() != null && !searchFgDelivery.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchFgDelivery.getCompanyCodeId()));
        }

        if (searchFgDelivery.getPlantId() != null && !searchFgDelivery.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchFgDelivery.getPlantId()));
        }

        if (searchFgDelivery.getLanguageId() != null && !searchFgDelivery.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchFgDelivery.getLanguageId()));
        }

        if (searchFgDelivery.getWarehouseId() != null && !searchFgDelivery.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchFgDelivery.getWarehouseId()));
        }

        if (searchFgDelivery.getOperationNumber() != null && !searchFgDelivery.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchFgDelivery.getOperationNumber()));
        }

        if (searchFgDelivery.getReceipeId() != null && !searchFgDelivery.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchFgDelivery.getReceipeId()));
        }

        if (searchFgDelivery.getProductionOrderNo() != null && !searchFgDelivery.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchFgDelivery.getProductionOrderNo()));
        }

        if (searchFgDelivery.getProductionOrderLineNo() != null && !searchFgDelivery.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchFgDelivery.getProductionOrderLineNo()));
        }

        if (searchFgDelivery.getItemCode() != null && !searchFgDelivery.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchFgDelivery.getItemCode()));
        }

        if (searchFgDelivery.getStatusId() != null && !searchFgDelivery.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchFgDelivery.getStatusId()));
        }

        if (searchFgDelivery.getStartCreatedOn()!= null && searchFgDelivery.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchFgDelivery.getStartCreatedOn(),
                    searchFgDelivery.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }

}