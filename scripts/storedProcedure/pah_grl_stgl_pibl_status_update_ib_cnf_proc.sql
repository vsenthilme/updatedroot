-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, String preInboundNo, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE [dbo].[pah_grl_stgl_pibl_status_update_ib_cnf_proc]  
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@refDocNumber nvarchar(25),
	@preInboundNo nvarchar(25), 	
	@statusId bigint,
	@statusId2 bigint,
	@statusDescription nvarchar(50),	
	@statusDescription2 nvarchar(50),
	@updatedBy nvarchar(50),
	@updatedOn DATETIME
		
AS
BEGIN

	UPDATE PAH SET PAH.STATUS_ID = @statusId, PAH.STATUS_TEXT = @statusDescription, 
	PAH.PA_CNF_BY = @updatedBy, PAH.PA_CNF_ON = @updatedOn
	FROM tblputawayheader PAH INNER JOIN
	(SELECT C_ID,PLANT_ID,LANG_ID,WH_ID,REF_DOC_NO,PRE_IB_NO,IB_LINE_NO,ITM_CODE,MFR_NAME FROM tblinboundline
	WHERE 
			IS_DELETED = 0 AND REF_FIELD_2 = 'TRUE' AND status_id = @statusId AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo
	) X ON
	PAH.C_ID = X.C_ID AND PAH.PLANT_ID = X.PLANT_ID AND PAH.LANG_ID = X.LANG_ID AND PAH.WH_ID = X.WH_ID AND 
	PAH.REF_DOC_NO = X.REF_DOC_NO AND PAH.PRE_IB_NO = X.PRE_IB_NO AND PAH.ref_field_5 = X.ITM_CODE AND 
	PAH.MFR_NAME = X.MFR_NAME AND PAH.REF_FIELD_9 = X.IB_LINE_NO AND PAH.IS_DELETED = 0

	UPDATE PIBL SET PIBL.STATUS_ID = @statusId, PIBL.STATUS_TEXT = @statusDescription, 
	PIBL.UTD_BY = @updatedBy, PIBL.UTD_ON = @updatedOn
	FROM tblpreinboundline PIBL INNER JOIN
	(SELECT C_ID,PLANT_ID,LANG_ID,WH_ID,REF_DOC_NO,PRE_IB_NO,IB_LINE_NO,ITM_CODE,MFR_NAME FROM tblinboundline
	WHERE 
			IS_DELETED = 0 AND REF_FIELD_2 = 'TRUE' AND status_id = @statusId AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo
	) X ON
	PIBL.C_ID = X.C_ID AND PIBL.PLANT_ID = X.PLANT_ID AND PIBL.LANG_ID = X.LANG_ID AND PIBL.WH_ID = X.WH_ID AND 
	PIBL.REF_DOC_NO = X.REF_DOC_NO AND PIBL.PRE_IB_NO = X.PRE_IB_NO AND PIBL.ITM_CODE = X.ITM_CODE AND 
	PIBL.MFR_NAME = X.MFR_NAME AND PIBL.IB_LINE_NO = X.IB_LINE_NO AND PIBL.IS_DELETED = 0

	UPDATE GRL SET GRL.STATUS_ID = @statusId, GRL.STATUS_TEXT = @statusDescription, 
	GRL.GR_CNF_BY = @updatedBy, GRL.GR_CNF_ON = @updatedOn
	FROM tblgrline GRL INNER JOIN
	(SELECT C_ID,PLANT_ID,LANG_ID,WH_ID,REF_DOC_NO,PRE_IB_NO,IB_LINE_NO,ITM_CODE,MFR_NAME FROM tblinboundline
	WHERE 
			IS_DELETED = 0 AND REF_FIELD_2 = 'TRUE' AND status_id = @statusId AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo
	) X ON
	GRL.C_ID = X.C_ID AND GRL.PLANT_ID = X.PLANT_ID AND GRL.LANG_ID = X.LANG_ID AND GRL.WH_ID = X.WH_ID AND 
	GRL.REF_DOC_NO = X.REF_DOC_NO AND GRL.PRE_IB_NO = X.PRE_IB_NO AND GRL.ITM_CODE = X.ITM_CODE AND 
	GRL.MFR_NAME = X.MFR_NAME AND GRL.IB_LINE_NO = X.IB_LINE_NO AND GRL.IS_DELETED = 0 AND GRL.STATUS_ID != @statusId

	UPDATE STGL SET STGL.STATUS_ID = @statusId2, STGL.STATUS_TEXT = @statusDescription2, 
	STGL.ST_CNF_BY = @updatedBy, STGL.ST_CNF_ON = @updatedOn
	FROM tblstagingline STGL INNER JOIN
	(SELECT C_ID,PLANT_ID,LANG_ID,WH_ID,REF_DOC_NO,PRE_IB_NO,IB_LINE_NO,ITM_CODE,MFR_NAME FROM tblinboundline
	WHERE 
			IS_DELETED = 0 AND REF_FIELD_2 = 'TRUE' AND status_id = 24 AND 
			C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
			REF_DOC_NO = @refDocNumber AND PRE_IB_NO = @preInboundNo
	) X ON
	STGL.C_ID = X.C_ID AND STGL.PLANT_ID = X.PLANT_ID AND STGL.LANG_ID = X.LANG_ID AND STGL.WH_ID = X.WH_ID AND 
	STGL.REF_DOC_NO = X.REF_DOC_NO AND STGL.PRE_IB_NO = X.PRE_IB_NO AND STGL.ITM_CODE = X.ITM_CODE AND 
	STGL.MFR_NAME = X.MFR_NAME AND STGL.IB_LINE_NO = X.IB_LINE_NO AND STGL.IS_DELETED = 0

END