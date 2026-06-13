SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- String companyCodeId, String plantId, String languageId, String warehouseId
-- String refDocNumber, Long statusId, String statusDescription,
CREATE OR ALTER PROCEDURE [dbo].[quality_header_update_proc] 
	@companyCodeId nvarchar(5), 
	@plantId nvarchar(5), 
	@languageId nvarchar(5),
	@warehouseId nvarchar(5), 
	@qualityInspectionNo nvarchar(25),
	@statusId bigint,
	@statusDescription nvarchar(50),
    @created nvarchar(50)
		
AS
BEGIN

    DECLARE @createdBy nvarchar(50), @condition int
    
    select @createdBy = qc_ctd_by from tblqualityheader WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		  QC_NO = @qualityInspectionNo AND IS_DELETED = 0;

    IF @createdBy = 'MW_AMS'
	BEGIN
		SET @condition = 1
	END

    IF @createdBy <> 'MW_AMS'
	BEGIN
		SET @condition = 0
	END

    IF @condition = 0
    BEGIN
	UPDATE tblqualityheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, ref_field_10 = @statusDescription
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		  QC_NO = @qualityInspectionNo AND IS_DELETED = 0
    END

    IF @condition = 1
    BEGIN
	UPDATE tblqualityheader
	SET STATUS_ID = @statusId, STATUS_TEXT = @statusDescription, ref_field_10 = @statusDescription, qc_ctd_by = @created
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND WH_ID = @warehouseId AND 
		  QC_NO = @qualityInspectionNo AND IS_DELETED = 0
    END

END			
GO
