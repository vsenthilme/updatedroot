//package com.tekclover.wms.api.transaction.repository;
//
//import com.tekclover.wms.api.transaction.model.inbound.gr.GrHeaderV2;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Repository;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//@Transactional
//public interface GrHeaderV2Repository extends JpaRepository<GrHeaderV2,Long>, JpaSpecificationExecutor<GrHeaderV2> {
//
//	public List<GrHeaderV2> findAll();
//
//	Optional<GrHeaderV2> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndPreInboundNoAndRefDocNumberAndStagingNoAndGoodsReceiptNoAndPalletCodeAndCaseCodeAndDeletionIndicator(String languageId, String companyCode, String plantId, String warehouseId, String preInboundNo, String refDocNumber, String stagingNo, String goodsReceiptNo, String palletCode, String caseCode, long l);
//}