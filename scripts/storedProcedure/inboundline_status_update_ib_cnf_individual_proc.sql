USE WMS_ALMDEV
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String itemCode, String manufacturerName, Long lineNo, Date updatedOn, 
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE inboundline_status_update_ib_cnf_individual_proc 
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
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE tblinboundline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, REF_FIELD_2 = 'TRUE',
	IB_CNF_ON = @updatedOn, IB_CNF_BY = @updatedBy	
	WHERE 
		IS_DELETED = 0 AND status_id <> 24 AND ITM_CODE = @itemCode AND MFR_NAME = @manufacturerName AND
		C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo AND IB_LINE_NO = @lineNumber

END			
GO