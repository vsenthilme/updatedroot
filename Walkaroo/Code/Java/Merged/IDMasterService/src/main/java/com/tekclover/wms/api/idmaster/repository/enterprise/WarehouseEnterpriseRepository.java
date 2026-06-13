package com.tekclover.wms.api.idmaster.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tekclover.wms.api.idmaster.model.enterprise.warehouse.Warehouse;


@Repository
@Transactional
public interface WarehouseEnterpriseRepository extends JpaRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse> {

    public List<Warehouse> findByZone(String zone);

    public List<Warehouse> findByCompanyIdAndPlantIdAndLanguageIdAndWarehouseIdAndZoneAndDeletionIndicator(
            String companyCode, String plantId, String languageId, String warehouseId, String zone, Long deletionIndicator);
}