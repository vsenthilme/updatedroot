-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE reversal_line_status_update_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25),
	@itemCode nvarchar(50),
	@manufacturerName nvarchar(25),
	@lineNumber bigint, 	
	@grLineDeleteStatus bigint,
	@statusId bigint,
	@statusDescription nvarchar(50),	
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	IF @grLineDeleteStatus = 0
	BEGIN
		UPDATE tblinboundline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
		UTD_ON = @updatedOn, UTD_BY = @updatedBy	
		WHERE 
			IS_DELETED = 0 AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber
			
		UPDATE tblgrline SET IS_DELETED = 1
		WHERE 
			IS_DELETED = 0 AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber	
	END
		
	UPDATE tblpreinboundline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	UTD_BY = @updatedBy, UTD_ON = @updatedOn
	WHERE 
		IS_DELETED = 0 AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber

END