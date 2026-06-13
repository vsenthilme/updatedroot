USE WMS_ALMDEV
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE outbound_header_update_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@statusId bigint,
	@statusDescription nvarchar(50),
	@deliveryConfirmedOn DATETIME
		
AS
BEGIN

	UPDATE tbloutboundheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, DLV_CNF_ON = @deliveryConfirmedOn
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		  REF_DOC_NO = @refDocNumber AND IS_DELETED = 0
END			
GO