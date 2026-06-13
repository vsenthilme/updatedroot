-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE all_status_update_ib_reversal_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),	
	@barcodeId nvarchar(50),	
	@statusId bigint,
	@statusDescription nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN
			
	UPDATE tblputawayheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PA_CNF_BY = @updatedBy, PA_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND BARCODE_ID = @barcodeId
	
	UPDATE tblputawayline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	PA_CNF_BY = @updatedBy, PA_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND BARCODE_ID = @barcodeId
			
	UPDATE tblpreinboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	UTD_BY = @updatedBy, UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND BARCODE_ID = @barcodeId
	
	UPDATE tblgrline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	GR_CNF_BY = @updatedBy, GR_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND BARCODE_ID = @barcodeId

	UPDATE tblstagingline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	ST_CNF_BY = @updatedBy, ST_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND PARTNER_ITEM_BARCODE = @barcodeId

	UPDATE tblinboundline
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, 
	IB_CNF_BY = @updatedBy, IB_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId 
			AND REF_DOC_NO = @refDocNumber AND BARCODE_ID = @barcodeId

END