package com.tekclover.wms.api.transaction.model.outbound;

import java.util.Date;

public interface OutboundHeaderStream {

	public String getLanguageId();
	public String getCompanyCodeId();
	public String getPlantId();
	public String getWarehouseId();
	public String getPreOutboundNo();
	public String getRefDocNumber();
	public String getPartnerCode();
	public String getDeliveryOrderNo();
	public String getReferenceDocumentType();
	public Long getOutboundOrderTypeId();
	public Long getStatusId();
	public Date getRefDocDate();
	public Date getRequiredDeliveryDate();
	public String getReferenceField1();
	public String getReferenceField2();
	public String getReferenceField3();
	public String getReferenceField4();
	public String getReferenceField5();
	public String getReferenceField6();
	public String getReferenceField7();
	public String getReferenceField8();
	public String getReferenceField9();
	public String getReferenceField10();
	public Long getDeletionIndicator();
	public String getRemarks();
	public String getCreatedBy();
	public Date getCreatedOn();
	public String getDeliveryConfirmedBy();
	public Date getDeliveryConfirmedOn();
	public String getUpdatedBy();
	public Date getUpdatedOn();
	public String getReversedBy();
	public Date getReversedOn();
	public String getStatusDescription();
}
