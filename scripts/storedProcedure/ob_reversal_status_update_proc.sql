-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preOutboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE reversal_status_update_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preOutboundNo nvarchar(25),
	@barcodeId nvarchar(50),
	@itemCode nvarchar(50),
	@manufacturerName nvarchar(25),
	@lineNumber bigint,
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE tblpreoutboundheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PRE_OB_UTD_BY = @updatedBy, PRE_OB_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo

	UPDATE tbloutboundheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	DLV_CNF_BY = @updatedBy, DLV_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo	
	
	UPDATE tbloutboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	DLV_CNF_BY = @updatedBy, DLV_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo 
			AND OB_LINE_NO = @lineNumber AND ITM_CODE = @itemCode AND PARTNER_ITEM_BARCODE = @barcodeId AND MFR_NAME = @manufacturerName
			
	UPDATE tblpreoutboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PRE_OB_UTD_BY = @updatedBy, PRE_OB_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
			AND OB_LINE_NO = @lineNumber AND ITM_CODE = @itemCode AND BARCODE_ID = @barcodeId AND MFR_NAME = @manufacturerName
			
	UPDATE tblpickupline
	SET IS_DELETED = 1
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
			AND OB_LINE_NO = @lineNumber AND ITM_CODE = @itemCode AND PARTNER_ITEM_BARCODE = @barcodeId AND MFR_NAME = @manufacturerName
			
	UPDATE tblpickupheader
	SET IS_DELETED = 1
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
			AND OB_LINE_NO = @lineNumber AND ITM_CODE = @itemCode AND PARTNER_ITEM_BARCODE = @barcodeId AND MFR_NAME = @manufacturerName
						
	UPDATE tblqualityline
	SET IS_DELETED = 1
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
			AND OB_LINE_NO = @lineNumber AND ITM_CODE = @itemCode AND PARTNER_ITEM_BARCODE = @barcodeId AND MFR_NAME = @manufacturerName
						
	UPDATE tblqualityheader
	SET IS_DELETED = 1
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
			AND REF_FIELD_5 = @lineNumber AND REF_FIELD_4 = @itemCode AND PARTNER_ITEM_BARCODE = @barcodeId AND MFR_NAME = @manufacturerName
						
	UPDATE tbloutboundlinedup
	SET IS_DELETED = 1
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
			AND OB_LINE_NO = @lineNumber AND ITM_CODE = @itemCode AND PARTNER_ITEM_BARCODE = @barcodeId AND MFR_NAME = @manufacturerName

END