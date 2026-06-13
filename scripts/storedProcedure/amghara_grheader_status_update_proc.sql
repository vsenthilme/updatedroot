USE WMS_ALMDEV
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, String goodsReceiptNo
-- Long statusId, String statusDescription, Date updatedOn
CREATE OR ALTER PROCEDURE amghara_grheader_status_update_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5), 
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25), 
	@goodsReceiptNo nvarchar(25),
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedOn DATETIME
	
AS
BEGIN
	DECLARE @ROW_COUNT int

	SELECT @ROW_COUNT = COUNT(REF_DOC_NO) FROM tblstagingline 
	WHERE C_ID = @companyCodeId AND 
	PLANT_ID = @plantId AND 
	LANG_ID = @languageId AND 
	WH_ID = @warehouseId AND 
	REF_DOC_NO = @refDocNumber AND
	PRE_IB_NO = @preInboundNo AND
	IS_DELETED = 0 AND STATUS_ID <> 17
	GROUP BY REF_DOC_NO	

	IF @ROW_COUNT = 0
	BEGIN
		UPDATE tblgrheader
			SET STATUS_ID = @statusId,
			STATUS_TEXT = @statusDescription,
			GR_UTD_ON = @updatedOn,
			GR_CNF_ON = @updatedOn
			WHERE C_ID = @companyCodeId AND 
				PLANT_ID = @plantId AND 
				LANG_ID = @languageId AND 
				WH_ID = @warehouseId AND 
				REF_DOC_NO = @refDocNumber AND
				PRE_IB_NO = @preInboundNo AND
				IS_DELETED = 0
	END			
END				
GO