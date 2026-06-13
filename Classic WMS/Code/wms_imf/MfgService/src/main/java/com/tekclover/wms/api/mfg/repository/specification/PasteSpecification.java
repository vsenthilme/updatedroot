package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.paste.SearchPaste;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class PasteSpecification  implements Specification<Paste> {

    SearchPaste searchPaste;

    public PasteSpecification(SearchPaste inputSearchParams) {
        this.searchPaste = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<Paste> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchPaste.getCompanyCodeId() != null && !searchPaste.getCompanyCodeId().isEmpty()) {
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchPaste.getCompanyCodeId()));
        }

        if (searchPaste.getPlantId() != null && !searchPaste.getPlantId().isEmpty()) {
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchPaste.getPlantId()));
        }

        if (searchPaste.getLanguageId() != null && !searchPaste.getLanguageId().isEmpty()) {
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchPaste.getLanguageId()));
        }

        if (searchPaste.getWarehouseId() != null && !searchPaste.getWarehouseId().isEmpty()) {
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchPaste.getWarehouseId()));
        }

        if (searchPaste.getProductionOrderNo() != null && !searchPaste.getProductionOrderNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchPaste.getProductionOrderNo()));
        }

        if (searchPaste.getProductionOrderLineNo()!= null && !searchPaste.getProductionOrderLineNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchPaste.getProductionOrderLineNo()));
        }

        if (searchPaste.getReceipeId() != null && !searchPaste.getReceipeId().isEmpty()) {
            final Path<Group> group = root.get("recipeId");
            predicates.add(group.in(searchPaste.getReceipeId()));
        }

        if (searchPaste.getOperationNumber() != null && !searchPaste.getOperationNumber().isEmpty()) {
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchPaste.getOperationNumber()));
        }

        if (searchPaste.getItemCode() != null && !searchPaste.getItemCode().isEmpty()) {
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchPaste.getItemCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}