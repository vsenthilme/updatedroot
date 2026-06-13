USE WMS_ALMDEV
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String itemCode, String manufacturerName
CREATE OR ALTER PROCEDURE stagingline_inv_qty_update_proc 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25)
		
AS
BEGIN
	
	SELECT MAX(INV_ID) inventoryId into #invt FROM tblinventory GROUP BY ITM_CODE,MFR_NAME,ST_BIN

	SELECT C_ID,PLANT_ID,LANG_ID,WH_ID,ITM_CODE,MFR_NAME,SUM(REF_FIELD_4) invQty into #invtemptable FROM tblinventory 
	WHERE IS_DELETED = 0 AND BIN_CL_ID IN (1,7) 
	AND C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId
	AND INV_ID IN (SELECT inventoryId FROM #invt)
	GROUP BY ITM_CODE,MFR_NAME,PLANT_ID,WH_ID,C_ID,LANG_ID

	UPDATE TH SET TH.INV_QTY = X.invQty FROM tblstagingline TH INNER JOIN 
	(SELECT C_ID,PLANT_ID,LANG_ID,WH_ID,ITM_CODE,MFR_NAME,invQty FROM #invtemptable) X ON 
	X.C_ID = TH.C_ID AND X.PLANT_ID = TH.PLANT_ID AND X.LANG_ID = TH.LANG_ID AND X.WH_ID = TH.WH_ID 
	AND X.ITM_CODE = TH.ITM_CODE AND X.MFR_NAME = TH.MFR_NAME AND TH.REF_DOC_NO = @refDocNumber AND TH.PRE_IB_NO = @preInboundNo

END			
GO