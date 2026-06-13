package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.masterreceipe.MasterReceipe;
import com.tekclover.wms.api.mfg.model.masterreceipe.SearchMasterReceipe;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MasterReceipeSpecification implements Specification<MasterReceipe> {

	SearchMasterReceipe searchOperationMaster;

	public MasterReceipeSpecification(SearchMasterReceipe inputSearchParams) {
		this.searchOperationMaster = inputSearchParams;
	}
	 
	@Override
    public Predicate toPredicate(Root<MasterReceipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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

		 if (searchOperationMaster.getReceipeId() != null && !searchOperationMaster.getReceipeId().isEmpty()) {
        	 final Path<Group> group = root.get("receipeId");
        	 predicates.add(group.in(searchOperationMaster.getReceipeId()));
         }
         if (searchOperationMaster.getPhaseNumber() != null && !searchOperationMaster.getPhaseNumber().isEmpty()) {
        	 final Path<Group> group = root.get("phaseNumber");
        	 predicates.add(group.in(searchOperationMaster.getPhaseNumber()));
         }

		 if (searchOperationMaster.getChildItemCode() != null && !searchOperationMaster.getChildItemCode().isEmpty()) {
        	 final Path<Group> group = root.get("childItemCode");
        	 predicates.add(group.in(searchOperationMaster.getChildItemCode()));
         }
         
		 if (searchOperationMaster.getItemCode() != null && !searchOperationMaster.getItemCode().isEmpty()) {
        	 final Path<Group> group = root.get("itemCode");
        	 predicates.add(group.in(searchOperationMaster.getItemCode()));
         }

		 if (searchOperationMaster.getBomNumber() != null && !searchOperationMaster.getBomNumber().isEmpty()) {
        	 final Path<Group> group = root.get("bomNumber");
        	 predicates.add(group.in(searchOperationMaster.getBomNumber()));
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