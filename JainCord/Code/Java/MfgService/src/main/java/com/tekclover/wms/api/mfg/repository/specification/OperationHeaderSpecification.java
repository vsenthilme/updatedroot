package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.operation.OperationHeader;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationHeader;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OperationHeaderSpecification implements Specification<OperationHeader> {

	SearchOperationHeader searchOperationHeader;

	public OperationHeaderSpecification(SearchOperationHeader inputSearchParams) {
		this.searchOperationHeader = inputSearchParams;
	}
	 
	@Override
    public Predicate toPredicate(Root<OperationHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

         List<Predicate> predicates = new ArrayList<>();

         if (searchOperationHeader.getCompanyCodeId() != null && !searchOperationHeader.getCompanyCodeId().isEmpty()) {
        	 final Path<Group> group = root.get("companyCodeId");
        	 predicates.add(group.in(searchOperationHeader.getCompanyCodeId()));
         }

		 if (searchOperationHeader.getPlantId() != null && !searchOperationHeader.getPlantId().isEmpty()) {
        	 final Path<Group> group = root.get("plantId");
        	 predicates.add(group.in(searchOperationHeader.getPlantId()));
         }

		 if (searchOperationHeader.getLanguageId() != null && !searchOperationHeader.getLanguageId().isEmpty()) {
        	 final Path<Group> group = root.get("languageId");
        	 predicates.add(group.in(searchOperationHeader.getLanguageId()));
         }

		 if (searchOperationHeader.getWarehouseId() != null && !searchOperationHeader.getWarehouseId().isEmpty()) {
        	 final Path<Group> group = root.get("warehouseId");
        	 predicates.add(group.in(searchOperationHeader.getWarehouseId()));
         }
         
         if (searchOperationHeader.getProductionOrderNo() != null && !searchOperationHeader.getProductionOrderNo().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderNo");
        	 predicates.add(group.in(searchOperationHeader.getProductionOrderNo()));
         }

         if (searchOperationHeader.getProductionOrderType() != null && !searchOperationHeader.getProductionOrderType().isEmpty()) {
        	 final Path<Group> group = root.get("productionOrderType");
        	 predicates.add(group.in(searchOperationHeader.getProductionOrderType()));
         }

		 if (searchOperationHeader.getStatusId() != null && !searchOperationHeader.getStatusId().isEmpty()) {
        	 final Path<Group> group = root.get("statusId");
        	 predicates.add(group.in(searchOperationHeader.getStatusId()));
         }
		 
		 if (searchOperationHeader.getStartCreatedOn()!= null && searchOperationHeader.getEndCreatedOn() != null) {
			 predicates.add(cb.between(root.get("createdOn"), searchOperationHeader.getStartCreatedOn(),
        			 searchOperationHeader.getEndCreatedOn()));
         }
		 
		 if (searchOperationHeader.getStartConfirmedOn()!= null && searchOperationHeader.getEndConfirmedOn() != null) {
			 predicates.add(cb.between(root.get("orderConfirmedOn"), searchOperationHeader.getStartConfirmedOn(),
        			 searchOperationHeader.getEndConfirmedOn()));
         }

		 predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
         return cb.and(predicates.toArray(new Predicate[] {}));
     }
}