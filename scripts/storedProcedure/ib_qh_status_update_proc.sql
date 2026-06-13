USE WMS_CORE
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String itemCode, String inboundQualityNumber, Long lineNo, Date updatedOn, 
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE ib_qh_status_update_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25), 
	@itemCode nvarchar(50), 
	@inboundQualityNumber nvarchar(25),
	@lineNumber nvarchar(10),
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(15),
	@updatedOn DATETIME
		
AS
BEGIN
	UPDATE tblinboundqualityheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, UTD_ON = @updatedOn, UTD_BY = @updatedBy
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND ITM_CODE = @itemCode AND 
			IB_QC_NO = @inboundQualityNumber AND REF_FIELD_9 = @lineNumber AND IS_DELETED = 0
END			
GO