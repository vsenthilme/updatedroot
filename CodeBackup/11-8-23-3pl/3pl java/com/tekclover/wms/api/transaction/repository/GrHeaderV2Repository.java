package com.tekclover.wms.api.transaction.repository;

import com.tekclover.wms.api.transaction.model.inbound.gr.v2.GrHeaderV2;
import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GrHeaderV2Repository extends JpaRepository<GrHeaderV2,Long>, JpaSpecificationExecutor<GrHeaderV2>,
									StreamableJpaSpecificationRepository<GrHeaderV2> {


	Optional<GrHeaderV2> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndPreInboundNoAndRefDocNumberAndStagingNoAndGoodsReceiptNoAndPalletCodeAndCaseCodeAndDeletionIndicator(
			String languageId, String companyCode, String plantId,
			String warehouseId, String preInboundNo, String refDocNumber,
			String stagingNo, String goodsReceiptNo, String palletCode, String caseCode, Long deletionIndicator);

    List<GrHeaderV2> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndGoodsReceiptNoAndCaseCodeAndRefDocNumberAndDeletionIndicator(
			String languageId, String companyCode, String plantId,
			String warehouseId, String goodsReceiptNo, String caseCode, String refDocNumber, Long deletionIndicator);
}