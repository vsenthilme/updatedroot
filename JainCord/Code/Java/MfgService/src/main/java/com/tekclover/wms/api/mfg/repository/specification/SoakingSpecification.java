package com.tekclover.wms.api.mfg.repository.specification;
import com.tekclover.wms.api.mfg.model.soaking.SearchSoaking;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SoakingSpecification implements Specification<Soaking> {

    SearchSoaking searchSoaking;

    public SoakingSpecification(SearchSoaking inputSearchParams) {
        this.searchSoaking = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<Soaking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchSoaking.getCompanyCodeId() != null && !searchSoaking.getCompanyCodeId().isEmpty()) {
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchSoaking.getCompanyCodeId()));
        }

        if (searchSoaking.getPlantId() != null && !searchSoaking.getPlantId().isEmpty()) {
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchSoaking.getPlantId()));
        }

        if (searchSoaking.getLanguageId() != null && !searchSoaking.getLanguageId().isEmpty()) {
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchSoaking.getLanguageId()));
        }

        if (searchSoaking.getWarehouseId() != null && !searchSoaking.getWarehouseId().isEmpty()) {
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchSoaking.getWarehouseId()));
        }

        if (searchSoaking.getProductionOrderNo() != null && !searchSoaking.getProductionOrderNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchSoaking.getProductionOrderNo()));
        }

        if (searchSoaking.getProductionOrderLineNo()!= null && !searchSoaking.getProductionOrderLineNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchSoaking.getProductionOrderLineNo()));
        }

        if (searchSoaking.getReceipeId() != null && !searchSoaking.getReceipeId().isEmpty()) {
            final Path<Group> group = root.get("receipeId");
            predicates.add(group.in(searchSoaking.getReceipeId()));
        }

        if (searchSoaking.getOperationNumber() != null && !searchSoaking.getOperationNumber().isEmpty()) {
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchSoaking.getOperationNumber()));
        }

        if (searchSoaking.getItemCode() != null && !searchSoaking.getItemCode().isEmpty()) {
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchSoaking.getItemCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}