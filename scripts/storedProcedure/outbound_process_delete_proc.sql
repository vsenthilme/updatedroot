CREATE OR ALTER PROCEDURE outbound_process_delete_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@outboundOrderTypeId nvarchar(25)
		
AS
BEGIN

	delete tblpreoutboundline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId
	delete tblpreoutboundheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId
	delete tblordermangementline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId
	delete tblordermangementheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId
	delete tbloutboundline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId
	delete tbloutboundheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId
	delete tblpickupheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND OB_ORD_TYP_ID = @outboundOrderTypeId

END