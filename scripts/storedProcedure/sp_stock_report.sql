USE WMS_ALMDEV
GO

CREATE OR ALTER PROCEDURE sp_stock_report
    @companyCodeId nvarchar(10),
    @plantId nvarchar(10),
    @languageId nvarchar(10),
    @warehouseId nvarchar(10),
    @itemCode nvarchar(255) = NULL,
	@manufacturerName nvarchar(255) = NULL,
	@itemText nvarchar(255) = NULL,
	@stockTypeText nvarchar(255) = NULL

AS
BEGIN

	Declare @binClassId int = NULL
	
    -- Validate the input parameters
    IF @companyCodeId IS NULL OR @companyCodeId = ''
    BEGIN
        RAISERROR('Invalid companyCodeId ', 16, 1)
        RETURN
    END
	
	IF @plantId IS NULL OR @plantId = ''
    BEGIN
        RAISERROR('Invalid plantId ', 16, 1)
        RETURN
    END
	
	IF @languageId IS NULL OR @languageId = ''
    BEGIN
        RAISERROR('Invalid languageId ', 16, 1)
        RETURN
    END
	
	IF @warehouseId IS NULL OR @warehouseId = ''
    BEGIN
        RAISERROR('Invalid warehouse id', 16, 1)
        RETURN
    END
	
    -- Set itemCode as Null when itemCode = 0 since Spring boot to stored procedure unable to send null value input
    IF @itemCode = '0'
	BEGIN
	SET @itemCode = NULL
	END

	-- Set manufacturerName as Null when manufacturerName = 0 since Spring boot to stored procedure unable to send null value input
    IF @manufacturerName = '0'
	BEGIN
	SET @manufacturerName = NULL
	END
	
	-- Set itemText as Null when itemText = 0 since Spring boot to stored procedure unable to send null value input
    IF @itemText = '0'
	BEGIN
	SET @itemText = NULL
	END
	
	-- Set @stockTypeText as Null when stockTypeText = 0 since Spring boot to stored procedure unable to send null value input
    IF @stockTypeText = '0'
	BEGIN
	SET @binClassId = NULL
	END
	IF @stockTypeText = '1'
	BEGIN
	SET @binClassId = 1
	END
	IF @stockTypeText = '7'
	BEGIN
	SET @binClassId = 7
	END
	
	-- Create Temporary Table STOCKREPORT
	create table #STOCKREPORT 
				(C_ID NVARCHAR(10), 
				PLANT_ID NVARCHAR(10), 
				LANG_ID NVARCHAR(10), 
				WH_ID NVARCHAR(10), 
				ITM_CODE NVARCHAR(200), 
				MFR_NAME NVARCHAR(100), 
				TEXT NVARCHAR(255), 
				INV_QTY FLOAT, 
				ALLOC_QTY FLOAT, 
				TOT_QTY FLOAT, 
				C_TEXT NVARCHAR(50), 
				PLANT_TEXT NVARCHAR(50), 
				WH_TEXT NVARCHAR(50), 
				PRIMARY KEY (C_ID,PLANT_ID,LANG_ID,WH_ID,ITM_CODE,MFR_NAME));

	-- itemCode and Description from imbasicData1 to temp table 
	INSERT INTO #STOCKREPORT(C_ID,PLANT_ID,WH_ID,LANG_ID,ITM_CODE,TEXT,MFR_NAME,C_TEXT,PLANT_TEXT,WH_TEXT) 
	SELECT C_ID,PLANT_ID,WH_ID,LANG_ID,ITM_CODE,TEXT,MFR_PART,C_TEXT,PLANT_TEXT,WH_TEXT FROM TBLIMBASICDATA1 
	WHERE C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND IS_DELETED = 0 
	AND WH_ID = @warehouseId
	AND ITM_CODE = ISNULL(@itemCode,itm_code)
	AND TEXT = ISNULL(@itemText,text)
	AND MFR_PART = ISNULL(@manufacturerName,MFR_PART)
	
	select max(inv_id) inventoryId into #inv from tblinventory 
	group by itm_code,mfr_name,st_bin
	
	--inv_qty from tblinventory to temp table
	UPDATE TH SET TH.INV_QTY = X.invQty,TH.ALLOC_QTY = X.allocQty,TH.TOT_QTY = X.totQty FROM #stockreport TH INNER JOIN 
	(select c_id, plant_id, lang_id, wh_id, itm_code, mfr_name, sum(INV_QTY) invQty, sum(ALLOC_QTY) allocQty, sum(REF_FIELD_4) totQty 
	from tblinventory 
	where 
	inv_id in (select inventoryId from #inv) AND 
	bin_cl_id = ISNULL(@binClassId,bin_cl_id) AND 
	bin_cl_id != 3 AND
	C_ID = @companyCodeId AND PLANT_ID = @plantId AND LANG_ID = @languageId AND IS_DELETED = 0 
	AND WH_ID = @warehouseId
	group by itm_code,mfr_name,c_id, plant_id, lang_id, wh_id
	) X ON 
	X.C_ID = TH.C_ID AND X.PLANT_ID = TH.PLANT_ID AND X.WH_ID = TH.WH_ID AND X.LANG_ID = TH.LANG_ID AND 
	X.ITM_CODE = TH.ITM_CODE AND X.MFR_NAME = TH.MFR_NAME
	
	TRUNCATE TABLE tblstockreportoutput
	
	INSERT INTO tblstockreportoutput(C_ID,PLANT_ID,LANG_ID,WH_ID,ITM_CODE,MFR_NAME,TEXT,INV_QTY,ALLOC_QTY,TOT_QTY,C_TEXT,PLANT_TEXT,WH_TEXT)

	(select 
	C_ID, 
	PLANT_ID, 
	LANG_ID, 
	WH_ID, 
	ITM_CODE, 
	MFR_NAME, 
	TEXT, 
	COALESCE(INV_QTY,0) invQty, 
	COALESCE(ALLOC_QTY,0) allocQty, 
	COALESCE(TOT_QTY,0) totQty, 
	C_TEXT, 
	PLANT_TEXT, 
	WH_TEXT 
	from  
	#stockreport)
		
END

GO