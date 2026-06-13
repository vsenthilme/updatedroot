-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE stgl_stgh_ibl_status_update_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25), 	
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE tblstagingline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	ST_UTD_BY = @updatedBy, ST_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo

	UPDATE tblstagingheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	ST_UTD_BY = @updatedBy, ST_UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo
	
	UPDATE tblinboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	UTD_BY = @updatedBy, UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo

END