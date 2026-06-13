-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE all_line_status_update_ib_cnf_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25),
	@itemCode nvarchar(50),
	@manufacturerName nvarchar(25),
	@lineNumber bigint, 	
	@statusId bigint,
	@statusId2 bigint,
	@statusDescription nvarchar(50),	
	@statusDescription2 nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE tblinboundline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, REF_FIELD_2 = 'TRUE',
	IB_CNF_ON = @updatedOn, IB_CNF_BY = @updatedBy	
	WHERE 
		IS_DELETED = 0 AND STATUS_ID <> @statusId AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber
		
	UPDATE tblputawayline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription,  
    PA_CNF_ON = @updatedOn
	WHERE 
		IS_DELETED = 0 AND STATUS_ID <> @statusId AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber

	UPDATE tblputawayheader SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PA_CNF_BY = @updatedBy, PA_CNF_ON = @updatedOn
	WHERE 
		IS_DELETED = 0 AND STATUS_ID <> @statusId AND REF_FIELD_5 = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND REF_FIELD_9 = @lineNumber

	UPDATE tblpreinboundline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	UTD_BY = @updatedBy, UTD_ON = @updatedOn
	WHERE 
		IS_DELETED = 0 AND STATUS_ID <> @statusId AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber

	UPDATE tblgrline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	GR_CNF_BY = @updatedBy, GR_CNF_ON = @updatedOn
	WHERE 
		IS_DELETED = 0 AND STATUS_ID <> @statusId AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber		

	UPDATE tblstagingline SET STATUS_ID = @statusId2, STATUS_TEXT = @statusDescription2, 
	ST_CNF_BY = @updatedBy, ST_CNF_ON = @updatedOn
	WHERE 
		IS_DELETED = 0 AND STATUS_ID <> @statusId2 AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber

END