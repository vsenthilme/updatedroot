//package com.tekclover.wms.api.transaction.service;
//
//import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
//import com.tekclover.wms.api.transaction.model.inbound.gr.*;
//import com.tekclover.wms.api.transaction.repository.GrHeaderV2Repository;
//import com.tekclover.wms.api.transaction.util.CommonUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityNotFoundException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class GrHeaderV2Service extends BaseService {
//
//	@Autowired
//	private GrHeaderV2Repository grHeaderRepository;
//
//	/**
//	 * getGrHeaders
//	 * @return
//	 */
//	public List<GrHeaderV2> getGrHeaders () {
//		List<GrHeaderV2> grHeaderList =  grHeaderRepository.findAll();
//		grHeaderList =
//				grHeaderList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
//		return grHeaderList;
//	}
//
//	/**
//	 * getGrHeader
//	 * @param goodsReceiptNo
//	 * @return
//	 */
//	public GrHeaderV2 getGrHeader (String warehouseId, String preInboundNo, String refDocNumber, String stagingNo,
//			String goodsReceiptNo, String palletCode, String caseCode) {
//		Optional<GrHeaderV2> grHeader =
//				grHeaderRepository.findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndPreInboundNoAndRefDocNumberAndStagingNoAndGoodsReceiptNoAndPalletCodeAndCaseCodeAndDeletionIndicator(
//							getLanguageId(),
//							getCompanyCode(),
//							getPlantId(),
//							warehouseId,
//							preInboundNo,
//							refDocNumber,
//							stagingNo,
//							goodsReceiptNo,
//							palletCode,
//							caseCode,
//							0L);
//		if (grHeader.isEmpty()) {
//			throw new BadRequestException("The given values: warehouseId:" + warehouseId +
//			",refDocNumber: " + refDocNumber +
//			",preInboundNo: " + preInboundNo +
//			",stagingNo: " + stagingNo +
//			",palletCode: " + palletCode +
//			",caseCode: " + caseCode +
//			",goodsReceiptNo: " + goodsReceiptNo +
//			" doesn't exist.");
//		}
//
//		return grHeader.get();
//	}
//
//	/**
//	 * createGrHeader
//	 * @param newGrHeader
//	 * @param loginUserID
//	 * @return
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	public GrHeaderV2 createGrHeader (GrHeaderV2 newGrHeader, String loginUserID)
//			throws IllegalAccessException, InvocationTargetException {
//		Optional<GrHeaderV2> grheader =
//				grHeaderRepository.findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndPreInboundNoAndRefDocNumberAndStagingNoAndGoodsReceiptNoAndPalletCodeAndCaseCodeAndDeletionIndicator(
//						getLanguageId(),
//						getCompanyCode(),
//						getPlantId(),
//						newGrHeader.getWarehouseId(),
//						newGrHeader.getPreInboundNo(),
//						newGrHeader.getRefDocNumber(),
//						newGrHeader.getStagingNo(),
//						newGrHeader.getGoodsReceiptNo(),
//						newGrHeader.getPalletCode(),
//						newGrHeader.getCaseCode(),
//						0L);
//		if (!grheader.isEmpty()) {
//			throw new BadRequestException("Record is getting duplicated with the given values");
//		}
//		GrHeaderV2 dbGrHeader = new GrHeaderV2();
//		log.info("newGrHeader : " + newGrHeader);
//		BeanUtils.copyProperties(newGrHeader, dbGrHeader, CommonUtils.getNullPropertyNames(newGrHeader));
//
//		dbGrHeader.setCompanyCode(getCompanyCode());
//		dbGrHeader.setPlantId(getPlantId());
//		dbGrHeader.setDeletionIndicator(0L);
//		dbGrHeader.setCreatedBy(loginUserID);
//		dbGrHeader.setUpdatedBy(loginUserID);
//		dbGrHeader.setCreatedOn(new Date());
//		dbGrHeader.setUpdatedOn(new Date());
//		return grHeaderRepository.save(dbGrHeader);
//	}
//
//	/**
//	 * updateGrHeader
//	 * @param loginUserID
//	 * @param goodsReceiptNo
//	 * @param updateGrHeader
//	 * @return
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	public GrHeaderV2 updateGrHeader (String warehouseId, String preInboundNo, String refDocNumber,
//									  String stagingNo, String goodsReceiptNo, String palletCode,
//									  String caseCode, String loginUserID, GrHeaderV2 updateGrHeader)
//			throws IllegalAccessException, InvocationTargetException {
//		GrHeaderV2 dbGrHeader = getGrHeader(warehouseId, preInboundNo, refDocNumber, stagingNo, goodsReceiptNo,
//				palletCode, caseCode);
//		BeanUtils.copyProperties(updateGrHeader, dbGrHeader, CommonUtils.getNullPropertyNames(updateGrHeader));
//
//		dbGrHeader.setUpdatedBy(loginUserID);
//		dbGrHeader.setUpdatedOn(new Date());
//		return grHeaderRepository.save(dbGrHeader);
//	}
//
//	/**
//	 * deleteGrHeader
//	 * @param loginUserID
//	 * @param goodsReceiptNo
//	 */
//	public void deleteGrHeader (String warehouseId, String preInboundNo, String refDocNumber, String stagingNo, String goodsReceiptNo, String palletCode, String caseCode, String loginUserID) {
//		GrHeaderV2 grHeader = getGrHeader(warehouseId, preInboundNo, refDocNumber, stagingNo, goodsReceiptNo,
//				palletCode, caseCode);
//		if ( grHeader != null) {
//			grHeader.setDeletionIndicator(1L);
//			grHeader.setUpdatedBy(loginUserID);
//			grHeaderRepository.save(grHeader);
//		} else {
//			throw new EntityNotFoundException("Error in deleting Id: " + goodsReceiptNo);
//		}
//	}
//}
