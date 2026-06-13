USE WMS_ALMDEV
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE nostock_status_update_new_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preOutboundNo nvarchar(25),
	@statusId bigint,
	@statusDescription nvarchar(50)
		
AS
BEGIN
	Declare @noStock int = 0, @lines int, @noStkLines int;
	 
	select @lines = count(ob_line_no) from tblordermangementline where 
	REF_DOC_NO = @refDocNumber and PRE_OB_NO = @preOutboundNo and 
	C_ID = @companyCodeId and PLANT_ID = @plantId and LANG_ID = @languageId and WH_ID = @warehouseId and IS_DELETED = 0 
	
	select @noStkLines = count(ob_line_no) from tblordermangementline where status_id=47 and 
	REF_DOC_NO = @refDocNumber and PRE_OB_NO = @preOutboundNo and 
	C_ID = @companyCodeId and PLANT_ID = @plantId and LANG_ID = @languageId and WH_ID = @warehouseId and IS_DELETED = 0 

	IF @lines = @noStkLines
	BEGIN
		SET @noStock = 1
	END

	IF @noStock = 1
	BEGIN
	UPDATE tblpreoutboundline SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription
	WHERE IS_DELETED = 0 AND C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
	AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo

	UPDATE tblpreoutboundheader SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription
	WHERE IS_DELETED = 0 AND C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
	AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo

	UPDATE tbloutboundheader SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription
	WHERE IS_DELETED = 0 AND C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
	AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo
	END
	

END			
GO