CREATE OR ALTER PROCEDURE ob_no_of_attempt_proc 
	@refDocNumber nvarchar(25),
	@outboundOrderTypeId bigint,
	@updatedOn DATETIME
		
AS
BEGIN

	DECLARE @ATTEMPT_COUNT int, @ATTEMPTS int, @PROCESSED_STATUS int

	SELECT @ATTEMPT_COUNT = number_of_attempts from tbloborder2 
	WHERE order_id = @refDocNumber AND outbound_order_typeid = @outboundOrderTypeId
	
	IF @ATTEMPT_COUNT is null
	BEGIN
		SET @ATTEMPTS = 1
		SET @PROCESSED_STATUS = 0
	END
	IF @ATTEMPT_COUNT = 1
	BEGIN
		SET @ATTEMPTS = 2
		SET @PROCESSED_STATUS = 0
	END
	IF @ATTEMPT_COUNT = 2
	BEGIN
		SET @ATTEMPTS = 3
		SET @PROCESSED_STATUS = 0
	END
	IF @ATTEMPT_COUNT = 3
	BEGIN
		SET @ATTEMPTS = 3
		SET @PROCESSED_STATUS = 100
	END

	UPDATE tbloborder2
	SET number_of_attempts = @ATTEMPT_COUNT, order_processed_on = @updatedOn, processed_status_id = @PROCESSED_STATUS
	WHERE order_id = @refDocNumber AND outbound_order_typeid = @outboundOrderTypeId

END