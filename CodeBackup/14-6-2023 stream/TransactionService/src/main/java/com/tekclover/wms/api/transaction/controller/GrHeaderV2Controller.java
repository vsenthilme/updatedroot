//package com.tekclover.wms.api.transaction.controller;
//
//import com.tekclover.wms.api.transaction.model.inbound.gr.*;
//import com.tekclover.wms.api.transaction.service.GrHeaderV2Service;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.SwaggerDefinition;
//import io.swagger.annotations.Tag;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//import java.util.stream.Stream;
//
//@Slf4j
//@Validated
//@Api(tags = {"GrHeaderv2"}, value = "GrHeaderv2  Operations related to GrHeaderV2Controller") // label for swagger
//@SwaggerDefinition(tags = {@Tag(name = "GrHeaderV2 ",description = "Operations related to GrHeader V2")})
//@RequestMapping("/grheaderv2")
//@RestController
//public class GrHeaderV2Controller {
//
//	@Autowired
//	GrHeaderV2Service grheaderService;
//
//    @ApiOperation(response = GrHeaderV2.class, value = "Get all GrHeaderv2 details") // label for swagger
//	@GetMapping("")
//	public ResponseEntity<?> getAll() {
//		List<GrHeaderV2> grheaderList = grheaderService.getGrHeaders();
//		return new ResponseEntity<>(grheaderList, HttpStatus.OK);
//	}
//
//    @ApiOperation(response = GrHeader.class, value = "Get a GrHeader") // label for swagger
//	@GetMapping("/{goodsReceiptNo}")
//	public ResponseEntity<?> getGrHeader(@PathVariable String goodsReceiptNo, @RequestParam String warehouseId,
//			@RequestParam String preInboundNo, @RequestParam String refDocNumber, @RequestParam String stagingNo,
//			@RequestParam String palletCode, @RequestParam String caseCode) {
//    	GrHeader grheader = grheaderService.getGrHeader(warehouseId, preInboundNo, refDocNumber, stagingNo, goodsReceiptNo,
//    			palletCode, caseCode);
//    	log.info("GrHeader : " + grheader);
//		return new ResponseEntity<>(grheader, HttpStatus.OK);
//	}
//
//
//	@ApiOperation(response = GrHeaderV2.class, value = "Create GrHeaderV2") // label for swagger
//	@PostMapping("")
//	public ResponseEntity<?> postGrHeader(@Valid @RequestBody GrHeaderV2 newGrHeader, @RequestParam String loginUserID)
//			throws IllegalAccessException, InvocationTargetException {
//		GrHeaderV2 createdGrHeader = grheaderService.createGrHeader(newGrHeader, loginUserID);
//		return new ResponseEntity<>(createdGrHeader , HttpStatus.OK);
//	}
//
//    @ApiOperation(response = GrHeaderV2.class, value = "Update GrHeaderV2") // label for swagger
//    @PatchMapping("/{goodsReceiptNo}")
//	public ResponseEntity<?> patchGrHeader(@PathVariable String goodsReceiptNo, @RequestParam String warehouseId,
//			@RequestParam String preInboundNo, @RequestParam String refDocNumber, @RequestParam String stagingNo,
//			@RequestParam String palletCode, @RequestParam String caseCode,
//			@Valid @RequestBody GrHeaderV2 updateGrHeader, @RequestParam String loginUserID)
//			throws IllegalAccessException, InvocationTargetException {
//		GrHeaderV2 createdGrHeader =
//				grheaderService.updateGrHeader(warehouseId, preInboundNo, refDocNumber,
//						stagingNo, goodsReceiptNo, palletCode, caseCode, loginUserID, updateGrHeader);
//		return new ResponseEntity<>(createdGrHeader , HttpStatus.OK);
//	}
//
//    @ApiOperation(response = GrHeaderV2.class, value = "Delete GrHeaderV2") // label for swagger
//	@DeleteMapping("/{goodsReceiptNo}")
//	public ResponseEntity<?> deleteGrHeader(@PathVariable String goodsReceiptNo, @RequestParam String warehouseId,
//			@RequestParam String preInboundNo, @RequestParam String refDocNumber, @RequestParam String stagingNo,
//			@RequestParam String palletCode, @RequestParam String caseCode, @RequestParam String loginUserID) {
//    	grheaderService.deleteGrHeader(warehouseId, preInboundNo, refDocNumber, stagingNo, goodsReceiptNo, palletCode, caseCode, loginUserID);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
//}