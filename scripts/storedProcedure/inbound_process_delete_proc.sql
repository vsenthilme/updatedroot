CREATE OR ALTER PROCEDURE inbound_process_delete_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@inboundOrderTypeId nvarchar(25)
		
AS
BEGIN

	delete tblpreinboundline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblpreinboundheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblinboundline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblinboundheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblstagingline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblstagingheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblgrheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblgrline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblputawayheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId
	delete tblputawayline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber AND IB_ORD_TYP_ID = @inboundOrderTypeId

END