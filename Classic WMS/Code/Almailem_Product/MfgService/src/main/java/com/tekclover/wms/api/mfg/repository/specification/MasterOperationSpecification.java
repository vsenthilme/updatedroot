package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.masteroperation.MasterOperation;
import com.tekclover.wms.api.mfg.model.masteroperation.SearchMasterOperation;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MasterOperationSpecification implements Specification<MasterOperation> {

	SearchMasterOperation searchOperationMaster;

	public MasterOperationSpecification(SearchMasterOperation inputSearchParams) {
		this.searchOperationMaster = inputSearchParams;
	}
	 
	@Override
    public Predicate toPredicate(Root<MasterOperation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

         List<Predicate> predicates = new ArrayList<>();

         if (searchOperationMaster.getCompanyCodeId() != null && !searchOperationMaster.getCompanyCodeId().isEmpty()) {
        	 final Path<Group> group = root.get("companyCodeId");
        	 predicates.add(group.in(searchOperationMaster.getCompanyCodeId()));
         }

		 if (searchOperationMaster.getPlantId() != null && !searchOperationMaster.getPlantId().isEmpty()) {
        	 final Path<Group> group = root.get("plantId");
        	 predicates.add(group.in(searchOperationMaster.getPlantId()));
         }

		 if (searchOperationMaster.getLanguageId() != null && !searchOperationMaster.getLanguageId().isEmpty()) {
        	 final Path<Group> group = root.get("languageId");
        	 predicates.add(group.in(searchOperationMaster.getLanguageId()));
         }

		 if (searchOperationMaster.getWarehouseId() != null && !searchOperationMaster.getWarehouseId().isEmpty()) {
        	 final Path<Group> group = root.get("warehouseId");
        	 predicates.add(group.in(searchOperationMaster.getWarehouseId()));
         }
         
         if (searchOperationMaster.getOperationNumber() != null && !searchOperationMaster.getOperationNumber().isEmpty()) {
        	 final Path<Group> group = root.get("operationNumber");
        	 predicates.add(group.in(searchOperationMaster.getOperationNumber()));
         }

		 if (searchOperationMaster.getPhaseNumber() != null && !searchOperationMaster.getPhaseNumber().isEmpty()) {
        	 final Path<Group> group = root.get("phaseNumber");
        	 predicates.add(group.in(searchOperationMaster.getPhaseNumber()));
         }
         
		 if (searchOperationMaster.getWorkCenterId() != null && !searchOperationMaster.getWorkCenterId().isEmpty()) {
        	 final Path<Group> group = root.get("workCenterId");
        	 predicates.add(group.in(searchOperationMaster.getWorkCenterId()));
         }
		 
		 if (searchOperationMaster.getStatusId() != null && !searchOperationMaster.getStatusId().isEmpty()) {
        	 final Path<Group> group = root.get("statusId");
        	 predicates.add(group.in(searchOperationMaster.getStatusId()));
         }	
		 
		 if (searchOperationMaster.getStartCreatedOn()!= null && searchOperationMaster.getEndCreatedOn() != null) {
			 predicates.add(cb.between(root.get("createdOn"), searchOperationMaster.getStartCreatedOn(),
        			 searchOperationMaster.getEndCreatedOn()));
         }

		 predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
         return cb.and(predicates.toArray(new Predicate[] {}));
     }
}