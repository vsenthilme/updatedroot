package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.peeling.SearchPeeling;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PeelingSpecification implements Specification<Peeling> {
    SearchPeeling searchPeeling;

    public PeelingSpecification(SearchPeeling inputSearchParams) {
        this.searchPeeling = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<Peeling> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchPeeling.getCompanyCodeId() != null && !searchPeeling.getCompanyCodeId().isEmpty()) {
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchPeeling.getCompanyCodeId()));
        }

        if (searchPeeling.getPlantId() != null && !searchPeeling.getPlantId().isEmpty()) {
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchPeeling.getPlantId()));
        }

        if (searchPeeling.getLanguageId() != null && !searchPeeling.getLanguageId().isEmpty()) {
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchPeeling.getLanguageId()));
        }

        if (searchPeeling.getWarehouseId() != null && !searchPeeling.getWarehouseId().isEmpty()) {
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchPeeling.getWarehouseId()));
        }

        if (searchPeeling.getProductionOrderNo() != null && !searchPeeling.getProductionOrderNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchPeeling.getProductionOrderNo()));
        }

        if (searchPeeling.getProductionOrderLineNo()!= null && !searchPeeling.getProductionOrderLineNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchPeeling.getProductionOrderLineNo()));
        }

        if (searchPeeling.getReceipeId() != null && !searchPeeling.getReceipeId().isEmpty()) {
            final Path<Group> group = root.get("receipeId");
            predicates.add(group.in(searchPeeling.getReceipeId()));
        }

        if (searchPeeling.getOperationNumber() != null && !searchPeeling.getOperationNumber().isEmpty()) {
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchPeeling.getOperationNumber()));
        }

        if (searchPeeling.getItemCode() != null && !searchPeeling.getItemCode().isEmpty()) {
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchPeeling.getItemCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}