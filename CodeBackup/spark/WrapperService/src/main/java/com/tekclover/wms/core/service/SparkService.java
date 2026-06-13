package com.tekclover.wms.core.service;

import com.tekclover.wms.core.model.Almailem.FindGrLineV2;
import com.tekclover.wms.core.model.Almailem.GrLineV2;
import com.tekclover.wms.core.model.spark.InventoryMovement;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

//@Slf4j
//@Service
@Component
public class SparkService {

//	Properties connProp = new Properties();

//	SparkSession sparkSession = null;

//	@Autowired
//	private SparkSession sparkSession;

//	@Autowired
//	private JavaSparkContext jsc;

//	SparkSession spark = SparkSession.builder().appName("SparkExample").master("local[*]").getOrCreate();

	Properties connProp = new Properties();
	SparkSession sparkSession = null;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public SparkService() throws ParseException {
		//connection properties
		connProp.setProperty("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connProp.put("user", "sa");
		connProp.put("password", "30NcyBuK");
		sparkSession = SparkSession.builder().master("local[*]").appName("GrLine.com").config("spark.executor.memory", "4g")
				.config("spark.executor.cores", "4").getOrCreate();

		//Read from Sql Table
		val df2 = sparkSession.read().option("fetchSize", "100000").jdbc("jdbc:sqlserver://35.154.84.178;databaseName=WMS_ALMDEV", "tblGrline", connProp)
				.repartition(16);
		df2.createOrReplaceTempView("tblGrlineV2");

	}

	public List<GrLineV2> findGrLineV2(FindGrLineV2 findGrLineV2) throws ParseException {

		Dataset<Row>
				grLineQueryV2 = sparkSession.sql("SELECT " +
				" LANG_ID as languageId, " +
				" C_ID as companyCode, " +
				" PLANT_ID as plantId, " +
				" WH_ID as warehouseId, " +
				" PRE_IB_NO as preInboundNo, " +
				" REF_DOC_NO as refDocNumber, " +
				" GR_NO as goodsReceiptNo, " +
				" PAL_CODE as palletCode," +
				"CASE_CODE as caseCode, " +
				" PACK_BARCODE as packBarcodes, " +
				"IB_LINE_NO as lineNo, " +
				"ITM_CODE as itemCode, " +
				"IB_ORD_TYP_ID as inboundOrderTypeId, " +
				"VAR_ID as variantCode, " +
				"VAR_SUB_ID as variantSubCode, " +
				"STR_NO as batchSerialNumber, " +
				"STCK_TYP_ID as stockTypeId, " +
				"SP_ST_IND_ID as specialStockIndicatorId, " +
				"ST_MTD as storageMethod, " +
				"STATUS_ID as statusId, " +
				"PARTNER_CODE as businessPartnerCode, " +
				"CONT_NO as containerNo, " +
				"INV_NO as invoiceNo, " +
				"ITEM_TEXT as itemDescription, " +
				"MFR_PART as manufacturerPartNo, " +
				"HSN_CODE as hsnCode, " +
				"VAR_TYP as variantType, " +
				"SPEC_ACTUAL as specificationActual, " +
				"ITM_BARCODE as itemBarcode, " +
				"ORD_QTY as orderQty, " +
				"ORD_UOM as orderUom, " +
				"GR_QTY as goodReceiptQty, " +
				"GR_UOM as grUom, " +
				"ACCEPT_QTY as acceptedQty, " +
				"DAMAGE_QTY as damageQty, " +
				"QTY_TYPE as quantityType, " +
				"ASS_USER_ID as assignedUserId, " +
				"PAWAY_HE_NO as putAwayHandlingEquipment, " +
				"PA_CNF_QTY as confirmedQty, " +
				"REM_QTY as remainingQty, " +
				"REF_ORD_NO as referenceOrderNo, " +
				"REF_ORD_QTY as referenceOrderQty, " +
				"CROSS_DOCK_ALLOC_QTY as crossDockAllocationQty, " +
				"MFR_DATE as manufacturerDate, " +
				"EXP_DATE as expiryDate, " +
				"STR_QTY as storageQty, " +
				"REMARK as remark, " +
				"REF_FIELD_1 as referenceField1, " +
				"REF_FIELD_2 as referenceField2, " +
				"REF_FIELD_3 as referenceField3, " +
				"REF_FIELD_4 as referenceField4, " +
				"REF_FIELD_5 as referenceField5, " +
				"REF_FIELD_6 as referenceField6, " +
				"REF_FIELD_7 as referenceField7, " +
				"REF_FIELD_8 as referenceField8, " +
				"REF_FIELD_9 as referenceField9, " +
				"REF_FIELD_10 as referenceField10, " +
				"IS_DELETED as deletionIndicator, " +
				"GR_CTD_BY as createdBy, " +
				"GR_CTD_ON as createdOn, " +
				"GR_UTD_BY as updatedBy, " +
				"GR_UTD_ON as updatedOn, " +
				"GR_CNF_BY as confirmedBy, " +
				"GR_CNF_ON as confirmedOn, " +
				"INV_QTY as inventoryQuantity, " +
				"BARCODE_ID as barcodeId, " +
				"CBM as cbm, " +
				"CBM_UNIT as cbmUnit, " +
				"MFR_CODE as manufacturerCode, " +
				"MFR_NAME as manufacturerName, " +
				"ORIGIN as origin, " +
				"BRAND as brand, " +
				"REJ_TYPE as rejectType, " +
				"REJ_REASON as rejectReason, " +
				"CBM_QTY as cbmQuantity, " +
				"C_TEXT as companyDescription, " +
				"PLANT_TEXT as plantDescription, " +
				"WH_TEXT as warehouseDescription, " +
				"ST_BIN_INTM as interimStorageBin, " +
				"STATUS_TEXT as statusDescription, " +
				"PURCHASE_ORDER_NUMBER as purchaseOrderNumber, " +
				"MIDDLEWARE_ID as middlewareId, " +
				"MIDDLEWARE_HEADER_ID as middlewareHeaderId, " +
				"MIDDLEWARE_TABLE as middlewareTable, " +
				"MANUFACTURER_FULL_NAME as manufacturerFullName, " +
				"REF_DOC_TYPE as referenceDocumentType, " +
				"BRANCH_CODE as branchCode, " +
				"TRANSFER_ORDER_NO as transferOrderNo, " +
				"IS_COMPLETED as isCompleted " +
				"FROM tblGrlineV2 WHERE IS_DELETED = 0 ");

		grLineQueryV2.cache();

		if (findGrLineV2.getLanguageId() != null && !findGrLineV2.getLanguageId().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("LANG_ID").isin(findGrLineV2.getLanguageId().toArray()));
		}
		if (findGrLineV2.getCompanyCodeId() != null && !findGrLineV2.getCompanyCodeId().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("C_ID").isin(findGrLineV2.getCompanyCodeId().toArray()));
		}
		if (findGrLineV2.getPlantId() != null && !findGrLineV2.getPlantId().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("PLANT_ID").isin(findGrLineV2.getPlantId().toArray()));
		}
		if (findGrLineV2.getWarehouseId() != null && !findGrLineV2.getWarehouseId().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("WH_ID").isin(findGrLineV2.getWarehouseId().toArray()));
		}

		if (findGrLineV2.getManufacturerCode() != null && !findGrLineV2.getManufacturerCode().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("MFR_CODE").isin(findGrLineV2.getManufacturerCode().toArray()));
		}

		if (findGrLineV2.getManufacturerName() != null && !findGrLineV2.getManufacturerName().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("MFR_NAME").isin(findGrLineV2.getManufacturerName().toArray()));
		}

		if (findGrLineV2.getOrigin() != null && !findGrLineV2.getOrigin().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("ORIGIN").isin(findGrLineV2.getOrigin().toArray()));
		}
		if (findGrLineV2.getBrand() != null && !findGrLineV2.getBrand().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("BRAND").isin(findGrLineV2.getBrand().toArray()));
		}
		if (findGrLineV2.getPreInboundNo() != null && !findGrLineV2.getPreInboundNo().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("PRE_IB_NO").isin(findGrLineV2.getPreInboundNo().toArray()));
		}
		if (findGrLineV2.getRefDocNumber() != null && !findGrLineV2.getRefDocNumber().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("REF_DOC_NO").isin(findGrLineV2.getRefDocNumber().toArray()));
		}

		if (findGrLineV2.getCaseCode() != null && !findGrLineV2.getCaseCode().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("CASE_CODE").isin(findGrLineV2.getCaseCode().toArray()));
		}

		if (findGrLineV2.getLineNo() != null && !findGrLineV2.getLineNo().isEmpty()) {
			List<String> lineNoString = findGrLineV2.getLineNo().stream().map(String::valueOf).collect(Collectors.toList());
			grLineQueryV2 = grLineQueryV2.filter(col("IB_LINE_NO").isin(lineNoString.toArray()));
		}
		if (findGrLineV2.getItemCode() != null && !findGrLineV2.getItemCode().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("ITM_CODE").isin(findGrLineV2.getItemCode().toArray()));
		}
		if (findGrLineV2.getStatusId() != null && !findGrLineV2.getStatusId().isEmpty()) {
			List<String> statusString = findGrLineV2.getStatusId().stream().map(String::valueOf).collect(Collectors.toList());
			grLineQueryV2 = grLineQueryV2.filter(col("STATUS_ID").isin(statusString.toArray()));
		}
		if (findGrLineV2.getInterimStorageBin() != null && !findGrLineV2.getInterimStorageBin().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("ST_BIN_INTM").isin(findGrLineV2.getInterimStorageBin().toArray()));
		}
		if (findGrLineV2.getRejectType() != null && !findGrLineV2.getRejectType().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("REJ_TYPE").isin(findGrLineV2.getRejectType().toArray()));
		}
		if (findGrLineV2.getRejectReason() != null && !findGrLineV2.getRejectReason().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("REJ_REASON").isin(findGrLineV2.getRejectReason().toArray()));
		}
		if (findGrLineV2.getPackBarcodes() != null && !findGrLineV2.getPackBarcodes().isEmpty()) {
			grLineQueryV2 = grLineQueryV2.filter(col("PACK_BARCODE").isin(findGrLineV2.getPackBarcodes().toArray()));
		}


		Encoder<GrLineV2> grLineEncoder = Encoders.bean(GrLineV2.class);
		Dataset<GrLineV2> dataSetControlGroup = grLineQueryV2.as(grLineEncoder);
		List<GrLineV2> result = dataSetControlGroup.collectAsList();

		return result;


	}

//	public List<String> getTableNames(String JDBC_URI,String JDBC_username,String JDBC_password) {
//		List<String> TablesList =  mysql_getTablesName(JDBC_URI, JDBC_username, JDBC_password);
//		return TablesList;
//	}
//
//	public List<String> mysql_getTablesName(String uri,String username,String password) {
//		String DB = uri.substring(uri.lastIndexOf("/") + 1);
//
//		String dbQuery = "(SELECT table_name FROM information_schema.tables WHERE table_schema = '"+DB+"') tmp";
//		Dataset<Row> mysql =
//				sparkSession.read().format("jdbc")
//						.option("url", uri)
//						.option("user", username)
//						.option("password", password)
//						.option("dbtable",dbQuery )
//						.load();
//
//		System.out.println("Total Tables "+mysql.count());
//		List<String> list_tables  = mysql.select("table_name").map(row -> row.mkString(), Encoders.STRING()).collectAsList();
//		return list_tables;
//	}

//	public SparkService() {
//
//		//connection properties
//		connProp.setProperty("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		connProp.put("user", "sa");
//		connProp.put("password", "30NcyBuK");
//		sparkSession = SparkSession.builder().master("local[*]").appName("SparkByExample.com").getOrCreate();
//
//		//Read from Sql Table
//		val df2 = sparkSession.read().jdbc("jdbc:sqlserver://35.154.84.178;databaseName=WMS", "tblinventorymovement", connProp)
//				.repartition(4);
//		df2.createOrReplaceTempView("tblinventorymovement");
//	}
//
//	public List<InventoryMovement> findInventoryMovement() throws ParseException {
//
//		/*
//		 * @Column(name = "") private Date ;
//		 */
//		Dataset<Row> queryDF = sparkSession.sql("SELECT top 100 LANG_ID as languageId, C_ID as companyCodeId, \r\n"
//				+ "	PLANT_ID as plantId, WH_ID as warehouseId, \r\n"
//				+ "	PAL_CODE as palletCode, CASE_CODE as caseCode, ITM_CODE as itemCode, \r\n"
//				+ "	SP_ST_IND_ID as specialStockIndicatorId,\r\n"
//				+ " IM_CTD_ON as createdOn \r\n" +  " FROM tblinventorymovement");
//
//		String fromDate = "2022-01-01 00:00:00";
//		String toDate = "2022-07-30 23:59:59";
//		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fromDate);
//		Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(toDate);
////		log.info("convertStringToDate-------> : " + date1);
//
////		 queryDF
////		.filter(a->a.getTimestamp(2).after(date1) && a.getTimestamp(2).before(date2))
////		.show();
////		 Date[] dateConv = DateUtils.addTimeToDates (searchInvoiceHeader.getStartInvoiceDate(), searchInvoiceHeader.getEndInvoiceDate());
//
//
//		Encoder<InventoryMovement> inventoryMovement = Encoders.bean(InventoryMovement.class);
//		Dataset<InventoryMovement> dataSetControlGroup = queryDF.as(inventoryMovement);
////		Dataset<InventoryMovement> dataSetControlGroup = queryDF.filter(a -> a.getTimestamp(8).after(date1) && a.getTimestamp(8).before(date2))
////				.as(inventoryMovement);
////		return dataSetControlGroup.collectAsList();
//		return dataSetControlGroup.collectAsList();
//	}

}