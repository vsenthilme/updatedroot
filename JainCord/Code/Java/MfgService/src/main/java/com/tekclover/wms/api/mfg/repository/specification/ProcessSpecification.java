package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.process.Process;
import com.tekclover.wms.api.mfg.model.process.SearchProcess;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ProcessSpecification implements Specification<Process> {

    SearchProcess searchProcess;

    public ProcessSpecification(SearchProcess inputSearchParams) {
        this.searchProcess = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchProcess.getCompanyCodeId() != null && !searchProcess.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchProcess.getCompanyCodeId()));
        }

        if (searchProcess.getPlantId() != null && !searchProcess.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchProcess.getPlantId()));
        }

        if (searchProcess.getLanguageId() != null && !searchProcess.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchProcess.getLanguageId()));
        }

        if (searchProcess.getWarehouseId() != null && !searchProcess.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchProcess.getWarehouseId()));
        }

        if (searchProcess.getOperationNumber() != null && !searchProcess.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchProcess.getOperationNumber()));
        }

        if (searchProcess.getReceipeId() != null && !searchProcess.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchProcess.getReceipeId()));
        }

        if (searchProcess.getProductionOrderNo() != null && !searchProcess.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchProcess.getProductionOrderNo()));
        }

        if (searchProcess.getProductionOrderLineNo() != null && !searchProcess.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchProcess.getProductionOrderLineNo()));
        }

        if (searchProcess.getItemCode() != null && !searchProcess.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchProcess.getItemCode()));
        }

        if (searchProcess.getStatusId() != null && !searchProcess.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchProcess.getStatusId()));
        }

        if (searchProcess.getStartCreatedOn()!= null && searchProcess.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchProcess.getStartCreatedOn(),
                    searchProcess.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}