-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preOutboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE all_line_status_update_ob_reversal_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),	
	@preOutboundNo nvarchar(25),	
	@itemCode nvarchar(100),	
	@manufacturerName nvarchar(25),	
	@lineReference bigint,
	@orderTypeId bigint,
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN
	
	UPDATE tbloutboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, DLV_QTY = null,
	DLV_REV_BY = @updatedBy, DLV_REV_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND OB_LINE_NO = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
			
	UPDATE tblpreoutboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PRE_OB_UTD_BY = @updatedBy, PRE_OB_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber  AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND OB_LINE_NO = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
	
	UPDATE tblordermangementline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PICK_UP_UTD_BY = @updatedBy, PICK_UP_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND OB_LINE_NO = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
			
	UPDATE tblpickupline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription,
	PICK_REV_BY = @updatedBy, PICK_REV_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND OB_LINE_NO = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
			
	UPDATE tblpickupheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription,
	PICK_REV_BY = @updatedBy, PICK_REV_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND OB_LINE_NO = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
	
	UPDATE tblqualityheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription,
	QC_REV_BY = @updatedBy, QC_REV_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND REF_FIELD_4 = @itemCode AND MFR_NAME = @manufacturerName AND REF_FIELD_5 = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
			
	UPDATE tblqualityline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription,
	QC_REV_BY = @updatedBy, QC_REV_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND ITM_CODE = @itemCode AND MANUFACTURER_NAME = @manufacturerName AND OB_LINE_NO = @lineReference
			AND PRE_OB_NO = @preOutboundNo AND OB_ORD_TYP_ID = @orderTypeId
END