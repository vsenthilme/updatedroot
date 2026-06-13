package com.iweb2b.api.integration.model.consignment.dto.emiratespost;

import java.util.List;
import lombok.Data;

@Data
public class EmiratesPost {
	private String awbNumber;
	private Weight weight;
	private Shipper shipper;
	private Consignee consignee;
	private Dimensions dimensions;
	private Account account;
	private String productCode;
	private String printType;
	private boolean sendMailToSender;
	private boolean sendMailToReceiver;
	private boolean isInsured;
	private List<CustomsDeclarations> customsDeclarations;
	private DeclaredValue declaredValue;
	private Long numberOfPieces;
	private String referenceNumber1;
	private String referenceNumber2;
	private String referenceNumber3;
	private String referenceNumber4;
	private String specialNotes;
	private String remarks;
	private String deliveryType;
	private String transportMode;
	private String deliveredDuty;
	private String serviceType;
	private Cod cod;
	private Insurance insurance;
	private String iossTaxNumber;
	private String bagTagId;
	private String masterAirwayBill;
	private String siteId;
	private Long locationId;
}