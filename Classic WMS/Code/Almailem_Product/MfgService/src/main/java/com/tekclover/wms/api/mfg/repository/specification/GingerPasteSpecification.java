package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.gingerpaste.GingerPaste;
import com.tekclover.wms.api.mfg.model.gingerpaste.SearchGingerPaste;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GingerPasteSpecification implements Specification<GingerPaste> {

    SearchGingerPaste searchGingerPaste;

    public GingerPasteSpecification(SearchGingerPaste inputSearchParams) {
        this.searchGingerPaste = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<GingerPaste> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (searchGingerPaste.getCompanyCodeId() != null && !searchGingerPaste.getCompanyCodeId().isEmpty()) {
            final Path<Group> group = root.get("companyCodeId");
            predicates.add(group.in(searchGingerPaste.getCompanyCodeId()));
        }

        if (searchGingerPaste.getPlantId() != null && !searchGingerPaste.getPlantId().isEmpty()) {
            final Path<Group> group = root.get("plantId");
            predicates.add(group.in(searchGingerPaste.getPlantId()));
        }

        if (searchGingerPaste.getLanguageId() != null && !searchGingerPaste.getLanguageId().isEmpty()) {
            final Path<Group> group = root.get("languageId");
            predicates.add(group.in(searchGingerPaste.getLanguageId()));
        }

        if (searchGingerPaste.getWarehouseId() != null && !searchGingerPaste.getWarehouseId().isEmpty()) {
            final Path<Group> group = root.get("warehouseId");
            predicates.add(group.in(searchGingerPaste.getWarehouseId()));
        }

        if (searchGingerPaste.getProductionOrderNo() != null && !searchGingerPaste.getProductionOrderNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderNo");
            predicates.add(group.in(searchGingerPaste.getProductionOrderNo()));
        }

        if (searchGingerPaste.getProductionOrderLineNo()!= null && !searchGingerPaste.getProductionOrderLineNo().isEmpty()) {
            final Path<Group> group = root.get("productionOrderLineNo");
            predicates.add(group.in(searchGingerPaste.getProductionOrderLineNo()));
        }

        if (searchGingerPaste.getReceipeId() != null && !searchGingerPaste.getReceipeId().isEmpty()) {
            final Path<Group> group = root.get("receipeId");
            predicates.add(group.in(searchGingerPaste.getReceipeId()));
        }

        if (searchGingerPaste.getOperationNumber() != null && !searchGingerPaste.getOperationNumber().isEmpty()) {
            final Path<Group> group = root.get("operationNumber");
            predicates.add(group.in(searchGingerPaste.getOperationNumber()));
        }

        if (searchGingerPaste.getItemCode() != null && !searchGingerPaste.getItemCode().isEmpty()) {
            final Path<Group> group = root.get("itemCode");
            predicates.add(group.in(searchGingerPaste.getItemCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}