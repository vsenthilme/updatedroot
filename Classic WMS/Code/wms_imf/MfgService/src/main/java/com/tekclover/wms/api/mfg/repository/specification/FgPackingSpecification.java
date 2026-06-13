package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.fgpacking.FgPacking;
import com.tekclover.wms.api.mfg.model.fgpacking.SearchFgPacking;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class FgPackingSpecification implements Specification<FgPacking> {

    SearchFgPacking searchFgPacking;

    public FgPackingSpecification(SearchFgPacking inputSearchParams){
        this.searchFgPacking = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<FgPacking> root, CriteriaQuery<?> query, CriteriaBuilder cb ){

        List<Predicate> predicates = new ArrayList<>();

        if(searchFgPacking.getCompanyCodeId() != null && !searchFgPacking.getCompanyCodeId().isEmpty()){
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchFgPacking.getCompanyCodeId()));
        }

        if(searchFgPacking.getPlantId() != null && !searchFgPacking.getPlantId().isEmpty()){
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchFgPacking.getPlantId()));
        }

        if(searchFgPacking.getLanguageId() != null && !searchFgPacking.getLanguageId().isEmpty()){
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchFgPacking.getLanguageId()));
        }

        if(searchFgPacking.getWarehouseId() != null && !searchFgPacking.getWarehouseId().isEmpty()){
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchFgPacking.getWarehouseId()));
        }

        if(searchFgPacking.getProductionOrderNo() != null && !searchFgPacking.getProductionOrderNo().isEmpty()){
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchFgPacking.getProductionOrderNo()));
        }

        if(searchFgPacking.getProductionOrderLineNo() != null && !searchFgPacking.getProductionOrderLineNo().isEmpty()){
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchFgPacking.getProductionOrderLineNo()));
        }

        if(searchFgPacking.getReceipeId() != null && !searchFgPacking.getReceipeId().isEmpty()){
            final Path<Group> group = root.get("receipeId");
            predicates.add(group.in(searchFgPacking.getReceipeId()));
        }

        if(searchFgPacking.getOperationNumber() != null && !searchFgPacking.getOperationNumber().isEmpty()){
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchFgPacking.getOperationNumber()));
        }

        if(searchFgPacking.getItemCode() != null && !searchFgPacking.getItemCode().isEmpty()){
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchFgPacking.getItemCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}