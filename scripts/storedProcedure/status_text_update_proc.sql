USE WMS_ALMDEV
GO

CREATE OR ALTER PROCEDURE status_text_update_proc 
	
	@languageId nvarchar(5), 	
	@statusId bigint,
	@statusText nvarchar(50),
	@oldStatusText nvarchar(50)
	
AS
BEGIN
	
update tblpreinboundline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0
update tblpreinboundheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblinboundline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblinboundheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblstagingline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblstagingheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblgrheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblgrline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblputawayline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblputawayheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 

update tblpreoutboundline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblpreoutboundheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblordermangementline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblordermangementheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tbloutboundline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tbloutboundheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblpickupline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblpickupheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblqualityline set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 
update tblqualityheader set status_text = @statusText where status_id = @statusId and status_text = @oldStatusText and lang_id = @languageId and is_deleted = 0 

END			
GO