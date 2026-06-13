USE WMS_ALMDEV
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo
CREATE OR ALTER PROCEDURE ibheader_pal_cnt_update_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25)
		
AS
BEGIN

	DECLARE @ROW_COUNT int

	SELECT @ROW_COUNT = COUNT(lineCount) from (
	SELECT COUNT(IB_LINE_NO) lineCount FROM tblputawayline 
	WHERE C_ID = @companyCodeId AND 
	PLANT_ID = @plantId AND 
	LANG_ID = @languageId AND 
	WH_ID = @warehouseId AND 
	REF_DOC_NO = @refDocNumber AND
	PRE_IB_NO = @preInboundNo AND
	IS_DELETED = 0 AND STATUS_ID IN (20,24)
	GROUP BY IB_LINE_NO,REF_DOC_NO,PRE_IB_NO,PLANT_ID,WH_ID,C_ID) x

	UPDATE tblinboundheader
	SET received_lines = @ROW_COUNT
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo

END