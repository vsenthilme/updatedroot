package com.iweb2b.api.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:application-messages.properties")
public class PropertiesConfig {

	@Value("${master.oauth.credentials.client_id}")
	private String clientId;
	
	@Value("${master.oauth.credentials.client_secret_key}")
	private String clientSecretKey;
	
	@Value("${master.oauth.grant_type}")
	private String grantType;
	
	@Value("${master.oauth.grant_type.username}")
	private String username;
	
	@Value("${master.oauth.grant_type.password}")
	private String password;
	
	//---------------------Shipsy APIs---------------------------------------------------
	// shipsy.api.server=https://demodashboardapi.shipsy.in
	@Value("${shipsy.api.server}")
	private String shipsyApiServer;
	
	// shipsy.api.auth.token
	@Value("${shipsy.api.auth.token}")
	private String shipsyApiAuthtoken;
	
	//------------------Softdata upload--------------------------------------------------
	// shipsy.api.softdata.upload=/api/client/integration/consignment/upload/softdata/v2
	@Value("${shipsy.api.softdata.upload}")
	private String shipsyApiSoftdataUpload;
	
	// shipsy.api.softdata.update=/api/client/integration/consignment/event
	@Value("${shipsy.api.softdata.update}")
	private String shipsyApiSoftdataUpdate;
	
	//------------------Consignment Tracking---------------------------------------------
	// shipsy.api.consignment.tracking=/api/client/integration/consignment/track
	@Value("${shipsy.api.consignment.tracking}")
	private String shipsyApiConsignmentTracking;
	
	// shipsy.api.order.cancel=/api/client/integration/consignment/cancellation
	@Value("${shipsy.api.order.cancel}")
	private String shipsyApiOrderCancel;
	
	//------------------Client Label Generation Upload-----------------------------------
	// shipsy.api.labelgen=/api/client/integration/consignment/shippinglabel/stream
	@Value("${shipsy.api.labelgen}")
	private String shipsyApiLabelGen;
	
	//------------------Webhook---------------------------------------------------------------
	// shipsy.api.asyad.webhook=https://apix.asyadexpress.com/v2/webhooks/incoming-integration
	@Value("${shipsy.api.asyad.webhook}")
	private String shipsyApiAsyadWebhook;
	
	//------------------Address-Update--------------------------------------------------------
	// shipsy.api.consignment.update=/api/client/integration/consignment/v1/update
	@Value("${shipsy.api.consignment.update}")
	private String shipsyApiConsignmentUpdate;
	
	//==============================================================================
	//-------------------JNT--------------------------------------------------------
	// jnt.customerCode=J0086003429
	@Value("${jnt.customerCode}")
	private String jntCustomerCode;
	
	// jnt.address=https://demoopenapi.jtjms-sa.com/webopenplatformapi/api/order/addOrder
	@Value("${jnt.address}")
	private String jntAddress;
	
	// jnt.printLabel.address=https://openapi.jtjms-sa.com/webopenplatformapi/api/order/printOrderUrl
	@Value("${jnt.printLabel.address}")
	private String jntPrintLabelAddress;
	
	// jnt.printLabel.address=https://openapi.jtjms-sa.com/webopenplatformapi/api/order/printOrder
	@Value("${jnt.printLabel.pdf.address}")
	private String jntPdfPrintLabelAddress;
	
	// jnt.apiAccount=292508153084379141
	@Value("${jnt.apiAccount}")
	private String jntApiAccount;
	
	// jnt.privateKey=a0a1047cce70493c9d5d29704f05d0d9
	@Value("${jnt.privateKey}")
	private String jntPrivateKey;
	
	// jnt.bz.signature=VdlpKaoq64AZ0yEsBkvt1A==
	@Value("${jnt.bz.signature}")
	private String jntBzSignature;
	
	//=================================================================================
	//-------------------QATAR-APIs----------------------------------------------------
	/*
	 * qp.address=https://web.qatarpost.qa/shipmentapi
	 * qp.token=/get/token
	 * qp.token.username=IwEx
	 * qp.token.password=fV87Br
	 * qp.partnerToken=/partner/authentication/get/authcode/
	 * qp.create=/partner/order/create
	 * qp.tracking=/partner/order/trackingNumbers
	 */
	@Value("${qp.address}")
	private String qpAddress;
	
	@Value("${qp.token}")
	private String qpToken;
	
	@Value("${qp.token.username}")
	private String qpTokenUsername;
	
	@Value("${qp.token.password}")
	private String qpTokenPassword;

	@Value("${qp.partnerToken}")
	private String qpPartnerToken;

	@Value("${qp.create}")
	private String qpCreate;

	@Value("${qp.tracking}")
	private String qpTracking;
	
	//========================================================================================
	//-------------------ALI_ALGHANIM_APIs----------------------------------------------------
	
	/*
	 * gnm.auth.token=a3b48c6d8e9f1023g4h56789j0k123lmnop45qr6stu78vwxyz90ab1c2d3e4f5gh
	 * gnm.api.order.status.update=https://tlb-stage.lrweb.co/wp-json/iwx_api/v1/update-order-status/
	 */
	@Value("${gnm.auth.token}")
	private String alialgmAuthToken;
	
	@Value("${gnm.api.order.status.update}")
	private String alialgmApiOrderStatusUpdate;
//===========================================================================================
	//===========================================================================================
	//-------------------AJEX-APIs---------------------------------------------------------------
	// ajx.uk.update.count=5
	@Value("${ajx.uk.update.count}")
	private Long ajxUniqueKeyUpdateCount;
	
	/*
	 * ajx.wh.url.token=https://auth-stage.aj-ex.com/realms/aone-stage/protocol/openid-connect/token
	 * ajx.wh.client_id=iw-client
	 * ajx.wh.client_secret=YRh52F2sTJszU4bz0rQmmlbBW0dGFjiN
	 * ajx.wh.url.pickupscan=https://api-aone-stage.aj-ex.com/third-party/api/v1/scans
	 */
	@Value("${ajx.wh.url.token}")
	private String ajxWebhookTokenUrl;
	
	@Value("${ajx.wh.client_id}")
	private String ajxWebhookClientId;
	
	@Value("${ajx.wh.client_secret}")
	private String ajxWebhookClientSecret;
	
	@Value("${ajx.wh.url.pickupscan}")
	private String ajxWebhookUrlPickupscan;
	
	//===========================================================================================
	//-------------------SHOPINI-APIs---------------------------------------------------------------
	/*
	 * shopini.merchant_id=MjgxMjI=
	 * shopini.secret_key=MjgxMjIhQCMkOTY0NzQwMDAwMA==
	 * shopini.order.create=https://www.shopiniexpress.com/apiexpresspackagetrack/create_shipment
	 * shopini.order.update=https://www.shopiniexpress.com/apiexpresspackagetrack/update_shipment
	 * shopini.order.cancel=https://www.shopiniexpress.com/apiexpresspackagetrack/exceptions
	 * shopini.order.status=https://www.shopiniexpress.com/apiexpresspackagetrack/shipment_status
	 */
	@Value("${shopini.merchant_id}")
	private String shopiniMerchantId;
	
	@Value("${shopini.secret_key}")
	private String shopiniSecretKey;
	
	@Value("${shopini.order.create}")
	private String shopiniOrderCreateUrl;
	
	@Value("${shopini.order.update}")
	private String shopiniOrderUpdateUrl;
	
	@Value("${shopini.order.cancel}")
	private String shopiniOrderCancelUrl;
	
	@Value("${shopini.order.status}")
	private String shopiniOrderStatusUrl;

	@Value("${shopini.order.all.status}")
	private String shopiniOrderAllStatusUrl;
	
	//===========================================================================================
	//-------------------FLOW-APIs---------------------------------------------------------------
	/*
	 * flow.shipsy.api.server=https://flowexpress.customerportalnew.shipsy.io
	 * flow.shipsy.api.auth.token=33d45bc7fe8d419a00ba47693c62c8
	 * 
	 * flow.shipsy.api.softdata.upload=/api/customer/integration/consignment/upload/softdata/v2
	 * flow.shipsy.api.softdata.update=/api/customer/integration/consignment/event
	 * flow.shipsy.api.consignment.tracking=/api/customer/integration/consignment/track
	 * flow.shipsy.api.consignment.update=/api/customer/integration/consignment/v1/update
	 * flow.shipsy.api.labelgen=/api/customer/integration/consignment/shippinglabel/stream
	 * flow.shipsy.api.asyad.webhook=https://apix.asyadexpress.com/v2/webhooks/incoming-integration
	 * flow.shipsy.api.order.cancel=/api/customer/integration/consignment/cancellation
	 */
	@Value("${flow.shipsy.api.server}")
	private String flowShipsyApiServer;
	
	@Value("${flow.shipsy.api.auth.token}")
	private String flowShipsyApiAuthtoken;
	
	@Value("${flow.shipsy.api.auth.token.uae}")
	private String flowShipsyApiUAEAuthtoken;
	
	//------------------Softdata upload--------------------------------------------------
	@Value("${flow.shipsy.api.softdata.upload}")
	private String flowShipsyApiSoftdataUpload;
	
	@Value("${flow.shipsy.api.softdata.update}")
	private String flowShipsyApiSoftdataUpdate;
	
	//------------------Consignment Tracking---------------------------------------------
	@Value("${flow.shipsy.api.consignment.tracking}")
	private String flowShipsyApiConsignmentTracking;
	
	@Value("${flow.shipsy.api.order.cancel}")
	private String flowShipsyApiOrderCancel;
	
	//------------------Client Label Generation Upload-----------------------------------
	@Value("${flow.shipsy.api.labelgen}")
	private String flowShipsyApiLabelGen;
	
	//------------------Webhook---------------------------------------------------------------
	@Value("${flow.shipsy.api.asyad.webhook}")
	private String flowShipsyApiAsyadWebhook;
	
	//------------------Address-Update--------------------------------------------------------
	@Value("${flow.shipsy.api.consignment.update}")
	private String flowShipsyApiConsignmentUpdate;
	
/*
	 * ---------------BOQ-Webhook-----------------------------------------------------------
	 * boq.api.url=https://beta-ofsdspservice.boutiqaat.com/DSPStatus/UploadDSPStatus
	 * boq.api.token=DSPStausAPI@b0utiqaat
	 */
	@Value("${boq.api.url}")
	private String boqApiUrl;
	
	@Value("${boq.api.token}")
	private String boqApiToken;

	/*
	 * ---------------EmiratesPost-Webhook-----------------------------------------------------------
	 * ep.api.softdata.create=https://cross-border-stg.epservices.ae/api/Shipments/create
	 * ep.api.auth.token=23ba3fccc642a478c192e823f7c3d413:a51a45a8abf84994a19a1dfb0f044c4e
	 */
	@Value("${ep.api.softdata.create}")
	private String epCreateApiUrl;

	@Value("${ep.api.softdata.track}")
	private String epTrackApiUrl;

	@Value("${ep.api.auth.token}")
	private String epApiToken;
}