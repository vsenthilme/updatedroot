-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE reversal_header_status_update_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25), 	
	@statusId1 bigint,
	@statusId2 bigint,
	@statusId3 bigint,
	@statusDescription1 nvarchar(50),
	@statusDescription2 nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE tblpreinboundheader
	SET STATUS_ID = @statusId1, STATUS_TEXT = @statusDescription1, 
	UTD_BY = @updatedBy, UTD_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo 

	UPDATE tblinboundheader
	SET STATUS_ID = @statusId1, STATUS_TEXT = @statusDescription1, 
	IB_CNF_BY = @updatedBy, IB_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo 
	
	UPDATE tblgrheader
	SET STATUS_ID = @statusId3, STATUS_TEXT = @statusDescription2, 
	GR_CNF_BY = @updatedBy, GR_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo 

	UPDATE tblstagingheader
	SET STATUS_ID = @statusId2, STATUS_TEXT = @statusDescription2, 
	ST_CNF_BY = @updatedBy, ST_CNF_ON = @updatedOn
	WHERE IS_DELETED = 0 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo 

END