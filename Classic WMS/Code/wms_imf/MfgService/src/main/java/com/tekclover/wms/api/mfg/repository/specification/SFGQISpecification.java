package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.sfgqi.SFGQI;
import com.tekclover.wms.api.mfg.model.sfgqi.SearchSFGQI;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SFGQISpecification implements Specification<SFGQI> {

    SearchSFGQI searchSFGQI;

    public SFGQISpecification(SearchSFGQI inputSearchParams) { this.searchSFGQI = inputSearchParams; }

    @Override
    public Predicate toPredicate(Root<SFGQI> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchSFGQI.getCompanyCodeId() != null && !searchSFGQI.getCompanyCodeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchSFGQI.getCompanyCodeId()));
        }

        if (searchSFGQI.getPlantId() != null && !searchSFGQI.getPlantId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("plantId");
            predicates.add(group.in(searchSFGQI.getPlantId()));
        }

        if (searchSFGQI.getLanguageId() != null && !searchSFGQI.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("languageId");
            predicates.add(group.in(searchSFGQI.getLanguageId()));
        }

        if (searchSFGQI.getWarehouseId() != null && !searchSFGQI.getWarehouseId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("warehouseId");
            predicates.add(group.in(searchSFGQI.getWarehouseId()));
        }

        if (searchSFGQI.getOperationNumber() != null && !searchSFGQI.getOperationNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("operationNumber");
            predicates.add(group.in(searchSFGQI.getOperationNumber()));
        }

        if (searchSFGQI.getReceipeId() != null && !searchSFGQI.getReceipeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("receipeId");
            predicates.add(group.in(searchSFGQI.getReceipeId()));
        }

        if (searchSFGQI.getProductionOrderNo() != null && !searchSFGQI.getProductionOrderNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchSFGQI.getProductionOrderNo()));
        }

        if (searchSFGQI.getProductionOrderLineNo() != null && !searchSFGQI.getProductionOrderLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchSFGQI.getProductionOrderLineNo()));
        }

        if (searchSFGQI.getItemCode() != null && !searchSFGQI.getItemCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("itemCode");
            predicates.add(group.in(searchSFGQI.getItemCode()));
        }

        if (searchSFGQI.getStatusId() != null && !searchSFGQI.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.get("statusId");
            predicates.add(group.in(searchSFGQI.getStatusId()));
        }

        if (searchSFGQI.getStartCreatedOn()!= null && searchSFGQI.getEndCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), searchSFGQI.getStartCreatedOn(),
                    searchSFGQI.getEndCreatedOn()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}