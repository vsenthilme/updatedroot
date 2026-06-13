package com.iweb2b.core.model.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity (name = "ShopiniWebhook")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblshopiniwebhook")
public class ShopiniWebhook {
	/*
	 *  {
			"success":true, "msg":"XXXXXXXXXXXXXXXXX", "msg_ar":"XXXXXXXXXXXX",
			"status_code":"200", "data": {
			"track_number":xxxxxxxxx,
			"shipment_status":[
				{"status": "Recieved","date_time":"2019-10-21 14:54:42"},
				{"status": "Record Created","date_time":"2019-10-21 14:54:42"},
				{"status": "Delivered","date_time":"2019-10-21 14:54:42"}
			}
		}
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPINI_HOOK_ID")
    private Long shopiniHookID = 1L;
    
    @Column(name = "TRACKING_NO")
	private String trackingNo;

    @Column(name="REFERENCE_NUMBER", columnDefinition = "nvarchar(100)" )
    private String referenceNumber;
    
    @Column(name = "SHIPMENT_STATUS")
	private String shipmentStatus;
    
    @Column(name = "ACTION_DATE")
	private String actionDate;
    
    @Column(name = "ITEM_ACTION_NAME")
	private String itemActionName;
    
    @Column(name = "LMD_SYNCED_STATUS", columnDefinition = "bigint default 0")
	private Long lmdStatus = 0L;
}