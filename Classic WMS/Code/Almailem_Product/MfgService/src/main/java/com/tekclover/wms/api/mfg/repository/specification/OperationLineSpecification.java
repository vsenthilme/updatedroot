package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.operation.OperationLine;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationLine;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OperationLineSpecification implements Specification<OperationLine> {

	SearchOperationLine searchOperationLine;

	public OperationLineSpecification(SearchOperationLine inputSearchParams) {
		this.searchOperationLine = inputSearchParams;
	}
	 
	@Override
    public Predicate toPredicate(Root<OperationLine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

         List<Predicate> predicates = new ArrayList<>();

         if (searchOperationLine.getCompanyCodeId() != null && !searchOperationLine.getCompanyCodeId().isEmpty()) {
        	 final Path<Group> group = root.get("companyCodeId");
        	 predicates.add(group.in(searchOperationLine.getCompanyCodeId()));
         }

		 if (searchOperationLine.getPlantId() != null && !searchOperationLine.getPlantId().isEmpty()) {
        	 final Path<Group> group = root.get("plantId");
        	 predicates.add(group.in(searchOperationLine.getPlantId()));
         }

		 if (searchOperationLine.getLanguageId() != null && !searchOperationLine.getLanguageId().isEmpty()) {
        	 final Path<Group> group = root.get("languageId");
        	 predicates.add(group.in(searchOperationLine.getLanguageId()));
         }

		 if (searchOperationLine.getWarehouseId() != null && !searchOperationLine.getWarehouseId().isEmpty()) {
        	 final Path<Group> group = root.get("warehouseId");
        	 predicates.add(group.in(searchOperationLine.getWarehouseId()));
         }
         
         if (searchOperationLine.getProductionOrderNo() != null && !searchOperationLine.getProductionOrderNo().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderNo");
        	 predicates.add(group.in(searchOperationLine.getProductionOrderNo()));
         }

         if (searchOperationLine.getItemCode() != null && !searchOperationLine.getItemCode().isEmpty()) {
        	 final Path<Group> group = root.get("itemCode");
        	 predicates.add(group.in(searchOperationLine.getItemCode()));
         }

         if (searchOperationLine.getProductionOrderType() != null && !searchOperationLine.getProductionOrderType().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderType");
        	 predicates.add(group.in(searchOperationLine.getProductionOrderType()));
         }

         if (searchOperationLine.getItemType() != null && !searchOperationLine.getItemType().isEmpty()) {
        	 final Path<Group> group = root.get("itemType");
        	 predicates.add(group.in(searchOperationLine.getItemType()));
         }

         if (searchOperationLine.getBatchNumber() != null && !searchOperationLine.getBatchNumber().isEmpty()) {
        	 final Path<Group> group = root.get("batchNumber");
        	 predicates.add(group.in(searchOperationLine.getBatchNumber()));
         }

         if (searchOperationLine.getReceipeId() != null && !searchOperationLine.getReceipeId().isEmpty()) {
        	 final Path<Group> group = root.get("receipeId");
        	 predicates.add(group.in(searchOperationLine.getReceipeId()));
         }

         if (searchOperationLine.getProductionOrderLineNo() != null && !searchOperationLine.getProductionOrderLineNo().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderLineNo");
        	 predicates.add(group.in(searchOperationLine.getProductionOrderLineNo()));
         }

		 if (searchOperationLine.getStatusId() != null && !searchOperationLine.getStatusId().isEmpty()) {
        	 final Path<Group> group = root.get("statusId");
        	 predicates.add(group.in(searchOperationLine.getStatusId()));
         }
		 
		 if (searchOperationLine.getStartCreatedOn()!= null && searchOperationLine.getEndCreatedOn() != null) {
			 predicates.add(cb.between(root.get("createdOn"), searchOperationLine.getStartCreatedOn(),
        			 searchOperationLine.getEndCreatedOn()));
         }
		 
		 if (searchOperationLine.getStartConfirmedOn()!= null && searchOperationLine.getEndConfirmedOn() != null) {
			 predicates.add(cb.between(root.get("orderConfirmedOn"), searchOperationLine.getStartConfirmedOn(),
        			 searchOperationLine.getEndConfirmedOn()));
         }

		 predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
         return cb.and(predicates.toArray(new Predicate[] {}));
     }
}