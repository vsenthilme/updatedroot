package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.fgreceiving.FgReceiving;
import com.tekclover.wms.api.mfg.model.fgreceiving.SearchFgReceiving;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class FgReceivingSpecification implements Specification<FgReceiving> {

    SearchFgReceiving searchFgReceiving;

    public FgReceivingSpecification(SearchFgReceiving inputSearchParams){
        this.searchFgReceiving = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<FgReceiving> root, CriteriaQuery<?> query, CriteriaBuilder cb ){

        List<Predicate> predicates = new ArrayList<>();

        if(searchFgReceiving.getCompanyCodeId() != null && !searchFgReceiving.getCompanyCodeId().isEmpty()){
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchFgReceiving.getCompanyCodeId()));
        }

        if(searchFgReceiving.getPlantId() != null && !searchFgReceiving.getPlantId().isEmpty()){
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchFgReceiving.getPlantId()));
        }

        if(searchFgReceiving.getLanguageId() != null && !searchFgReceiving.getLanguageId().isEmpty()){
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchFgReceiving.getLanguageId()));
        }

        if(searchFgReceiving.getWarehouseId() != null && !searchFgReceiving.getWarehouseId().isEmpty()){
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchFgReceiving.getWarehouseId()));
        }

        if(searchFgReceiving.getProductionOrderNo() != null && !searchFgReceiving.getProductionOrderNo().isEmpty()){
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchFgReceiving.getProductionOrderNo()));
        }

        if(searchFgReceiving.getProductionOrderLineNo() != null && !searchFgReceiving.getProductionOrderLineNo().isEmpty()){
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchFgReceiving.getProductionOrderLineNo()));
        }

        if(searchFgReceiving.getReceipeId() != null && !searchFgReceiving.getReceipeId().isEmpty()){
            final Path<Group> group = root.get("receipeId");
            predicates.add(group.in(searchFgReceiving.getReceipeId()));
        }

        if(searchFgReceiving.getOperationNumber() != null && !searchFgReceiving.getOperationNumber().isEmpty()){
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchFgReceiving.getOperationNumber()));
        }

        if(searchFgReceiving.getItemCode() != null && !searchFgReceiving.getItemCode().isEmpty()){
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchFgReceiving.getItemCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}