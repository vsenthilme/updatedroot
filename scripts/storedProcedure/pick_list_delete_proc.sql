CREATE OR ALTER PROCEDURE pick_list_delete_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preOutboundNo nvarchar(25),
	@updatedBy nvarchar(25),
	@updatedOn DATETIME
		
AS
BEGIN

	update tblpreoutboundline set IS_DELETED = 1, PRE_OB_UTD_BY = @updatedBy, PRE_OB_UTD_ON = @updatedOn 
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblpreoutboundheader set IS_DELETED = 1, PRE_OB_UTD_BY = @updatedBy, PRE_OB_UTD_ON = @updatedOn
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblordermangementline set IS_DELETED = 1, PICK_UP_UTD_BY = @updatedBy, PICK_UP_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblordermangementheader set IS_DELETED = 1, PICK_UP_UTD_BY = @updatedBy, PICK_UP_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tbloutboundlinedup set IS_DELETED = 1, DLV_UTD_BY = @updatedBy, DLV_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tbloutboundline set IS_DELETED = 1, DLV_UTD_BY = @updatedBy, DLV_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tbloutboundheader set IS_DELETED = 1, DLV_UTD_BY = @updatedBy, DLV_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblpickupline set IS_DELETED = 1, PICK_UTD_BY = @updatedBy, PICK_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblpickupheader set IS_DELETED = 1, PICK_UTD_BY = @updatedBy, PICK_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0

	update tblqualityline set IS_DELETED = 1, QC_UTD_BY = @updatedBy, QC_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblqualityheader set IS_DELETED = 1, QC_UTD_BY = @updatedBy, QC_UTD_ON = @updatedOn  
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND PRE_OB_NO = @preOutboundNo AND IS_DELETED = 0
	
	update tblinventorymovement set IS_DELETED = 1
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND REF_NO = @preOutboundNo AND IS_DELETED = 0
	
END