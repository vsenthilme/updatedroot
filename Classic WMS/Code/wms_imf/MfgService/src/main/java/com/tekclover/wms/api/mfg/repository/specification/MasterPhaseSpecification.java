package com.tekclover.wms.api.mfg.repository.specification;

import com.tekclover.wms.api.mfg.model.masterphase.MasterPhase;
import com.tekclover.wms.api.mfg.model.masterphase.SearchMasterPhase;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MasterPhaseSpecification implements Specification<MasterPhase> {

	SearchMasterPhase searchMasterPhase;

	public MasterPhaseSpecification(SearchMasterPhase inputSearchParams) {
		this.searchMasterPhase = inputSearchParams;
	}
	 
	@Override
    public Predicate toPredicate(Root<MasterPhase> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

         List<Predicate> predicates = new ArrayList<>();

         if (searchMasterPhase.getCompanyCodeId() != null && !searchMasterPhase.getCompanyCodeId().isEmpty()) {
        	 final Path<Group> group = root.get("companyCodeId");
        	 predicates.add(group.in(searchMasterPhase.getCompanyCodeId()));
         }

		 if (searchMasterPhase.getPlantId() != null && !searchMasterPhase.getPlantId().isEmpty()) {
        	 final Path<Group> group = root.get("plantId");
        	 predicates.add(group.in(searchMasterPhase.getPlantId()));
         }

		 if (searchMasterPhase.getLanguageId() != null && !searchMasterPhase.getLanguageId().isEmpty()) {
        	 final Path<Group> group = root.get("languageId");
        	 predicates.add(group.in(searchMasterPhase.getLanguageId()));
         }

		 if (searchMasterPhase.getWarehouseId() != null && !searchMasterPhase.getWarehouseId().isEmpty()) {
        	 final Path<Group> group = root.get("warehouseId");
        	 predicates.add(group.in(searchMasterPhase.getWarehouseId()));
         }

         if (searchMasterPhase.getPhaseNumber() != null && !searchMasterPhase.getPhaseNumber().isEmpty()) {
        	 final Path<Group> group = root.get("phaseNumber");
        	 predicates.add(group.in(searchMasterPhase.getPhaseNumber()));
         }
		 
		 if (searchMasterPhase.getStatusId() != null && !searchMasterPhase.getStatusId().isEmpty()) {
        	 final Path<Group> group = root.get("statusId");
        	 predicates.add(group.in(searchMasterPhase.getStatusId()));
         }	
		 
		 if (searchMasterPhase.getStartCreatedOn()!= null && searchMasterPhase.getEndCreatedOn() != null) {
			 predicates.add(cb.between(root.get("createdOn"), searchMasterPhase.getStartCreatedOn(),
        			 searchMasterPhase.getEndCreatedOn()));
         }

		 predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
         return cb.and(predicates.toArray(new Predicate[] {}));
     }
}