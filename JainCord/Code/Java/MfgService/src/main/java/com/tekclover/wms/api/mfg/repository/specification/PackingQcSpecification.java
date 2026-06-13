package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.packingqc.PackingQc;
import com.tekclover.wms.api.mfg.model.packingqc.SearchPackingQc;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PackingQcSpecification implements Specification<PackingQc> {

    SearchPackingQc searchPackingQc;

    public PackingQcSpecification(SearchPackingQc inputSearchParams) { this.searchPackingQc = inputSearchParams; }

    @Override
    public Predicate toPredicate(Root<PackingQc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchPackingQc.getCompanyCodeId() != null && !searchPackingQc.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchPackingQc.getCompanyCodeId()));
        }

        if (searchPackingQc.getPlantId() != null && !searchPackingQc.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchPackingQc.getPlantId()));
        }

        if (searchPackingQc.getLanguageId() != null && !searchPackingQc.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchPackingQc.getLanguageId()));
        }

        if (searchPackingQc.getWarehouseId() != null && !searchPackingQc.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchPackingQc.getWarehouseId()));
        }

        if (searchPackingQc.getOperationNumber() != null && !searchPackingQc.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchPackingQc.getOperationNumber()));
        }

        if (searchPackingQc.getReceipeId() != null && !searchPackingQc.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchPackingQc.getReceipeId()));
        }

        if (searchPackingQc.getProductionOrderNo() != null && !searchPackingQc.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchPackingQc.getProductionOrderNo()));
        }

        if (searchPackingQc.getProductionOrderLineNo() != null && !searchPackingQc.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchPackingQc.getProductionOrderLineNo()));
        }

        if (searchPackingQc.getItemCode() != null && !searchPackingQc.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchPackingQc.getItemCode()));
        }

        if (searchPackingQc.getStatusId() != null && !searchPackingQc.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchPackingQc.getStatusId()));
        }

        if (searchPackingQc.getStartCreatedOn()!= null && searchPackingQc.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchPackingQc.getStartCreatedOn(),
                    searchPackingQc.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}