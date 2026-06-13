-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preOutboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE all_status_update_ob_header_reversal_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),	
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE tblpreoutboundheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PRE_OB_UTD_BY = @updatedBy, PRE_OB_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber

	UPDATE tbloutboundheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	DLV_REV_BY = @updatedBy, DLV_REV_ON = @updatedOn
	WHERE IS_DELETED = 0 AND C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber	
	
END