package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationConsumption;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OperationConsumptionSpecification implements Specification<OperationConsumption> {

	SearchOperationConsumption searchOperationConsumption;

	public OperationConsumptionSpecification(SearchOperationConsumption inputSearchParams) {
		this.searchOperationConsumption = inputSearchParams;
	}
	 
	@Override
    public Predicate toPredicate(Root<OperationConsumption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

         List<Predicate> predicates = new ArrayList<>();

         if (searchOperationConsumption.getCompanyCodeId() != null && !searchOperationConsumption.getCompanyCodeId().isEmpty()) {
        	 final Path<Group> group = root.get("companyCodeId");
        	 predicates.add(group.in(searchOperationConsumption.getCompanyCodeId()));
         }

		 if (searchOperationConsumption.getPlantId() != null && !searchOperationConsumption.getPlantId().isEmpty()) {
        	 final Path<Group> group = root.get("plantId");
        	 predicates.add(group.in(searchOperationConsumption.getPlantId()));
         }

		 if (searchOperationConsumption.getLanguageId() != null && !searchOperationConsumption.getLanguageId().isEmpty()) {
        	 final Path<Group> group = root.get("languageId");
        	 predicates.add(group.in(searchOperationConsumption.getLanguageId()));
         }

		 if (searchOperationConsumption.getWarehouseId() != null && !searchOperationConsumption.getWarehouseId().isEmpty()) {
        	 final Path<Group> group = root.get("warehouseId");
        	 predicates.add(group.in(searchOperationConsumption.getWarehouseId()));
         }
         
         if (searchOperationConsumption.getProductionOrderNo() != null && !searchOperationConsumption.getProductionOrderNo().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderNo");
        	 predicates.add(group.in(searchOperationConsumption.getProductionOrderNo()));
         }

		if (searchOperationConsumption.getItemCode() != null && !searchOperationConsumption.getItemCode().isEmpty()) {
			final Path<Group> group = root.get("itemCode");
			predicates.add(group.in(searchOperationConsumption.getItemCode()));
		}

		if (searchOperationConsumption.getBomItem() != null && !searchOperationConsumption.getBomItem().isEmpty()) {
			 final Path<Group> group = root.get("bomItem");
			 predicates.add(group.in(searchOperationConsumption.getBomItem()));
		}

		if (searchOperationConsumption.getBatchNumber() != null && !searchOperationConsumption.getBatchNumber().isEmpty()) {
			final Path<Group> group = root.get("batchNumber");
			predicates.add(group.in(searchOperationConsumption.getBatchNumber()));
		}

		if (searchOperationConsumption.getIssuedBy() != null && !searchOperationConsumption.getIssuedBy().isEmpty()) {
			final Path<Group> group = root.get("issuedBy");
			predicates.add(group.in(searchOperationConsumption.getIssuedBy()));
		}

		 if (searchOperationConsumption.getOrderConfirmedBy() != null && !searchOperationConsumption.getOrderConfirmedBy().isEmpty()) {
        	 final Path<Group> group = root.get("orderConfirmedBy");
        	 predicates.add(group.in(searchOperationConsumption.getOrderConfirmedBy()));
         }

		 if (searchOperationConsumption.getStatusId() != null && !searchOperationConsumption.getStatusId().isEmpty()) {
        	 final Path<Group> group = root.get("statusId");
        	 predicates.add(group.in(searchOperationConsumption.getStatusId()));
         }

		 if (searchOperationConsumption.getProductionOrderLineNo() != null && !searchOperationConsumption.getProductionOrderLineNo().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderLineNo");
        	 predicates.add(group.in(searchOperationConsumption.getProductionOrderLineNo()));
         }
		 
		 if (searchOperationConsumption.getStartCreatedOn()!= null && searchOperationConsumption.getEndCreatedOn() != null) {
			 predicates.add(cb.between(root.get("createdOn"), searchOperationConsumption.getStartCreatedOn(),
        			 searchOperationConsumption.getEndCreatedOn()));
         }

		 if (searchOperationConsumption.getStartConfirmedOn()!= null && searchOperationConsumption.getEndConfirmedOn() != null) {
			 predicates.add(cb.between(root.get("orderConfirmedOn"), searchOperationConsumption.getStartConfirmedOn(),
        			 searchOperationConsumption.getEndConfirmedOn()));
         }

		 predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
         return cb.and(predicates.toArray(new Predicate[] {}));
     }
}