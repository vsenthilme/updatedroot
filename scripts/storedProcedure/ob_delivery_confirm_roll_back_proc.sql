CREATE OR ALTER PROCEDURE ob_delivery_confirm_roll_back_proc
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25)
		
AS
BEGIN

	delete tblqualityheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber
	delete tblqualityline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber
	delete tblpickupline WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND REF_DOC_NO = @refDocNumber

END
