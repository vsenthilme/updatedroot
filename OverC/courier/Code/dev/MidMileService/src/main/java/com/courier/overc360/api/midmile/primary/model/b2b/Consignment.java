package com.courier.overc360.api.midmile.primary.model.b2b;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class Consignment {

    private String companyId = "IWE";
    private String customer_code;
    private String reference_number;
    private String service_type_id;
    private String consignment_type;
    private String load_type;
    private String description;
    private String cod_favor_of;
    private String cod_collection_mode;
    private String dimension_unit;
    private String length;
    private String width;
    private String height;
    private String weight_unit;
    private double weight;
    private double declared_value;
    private String cod_amount;
    private long num_pieces;
    private String notes;
    private String customer_reference_number;
    private Boolean is_risk_surcharge_applicable;
    private Date created_at;
    private String status_description;
    private String awb_3rd_Party;
    private String scanType;
    private String hubCode;
    private String action_time;
    private String qpWebhookStatus;
    private String orderType;
    private String jntPushStatus;
    private String boutiqaatPushStatus;
    private String customer_civil_id;
    private String receiver_civil_id;
    private String currency;
    private Boolean is_awb_printed;
    private String inco_terms;
    
    private Origin_Details origin_details;
	
    private Destination_Details destination_details;
    
    private Set<Pieces_Details> pieces_detail;
    private String hsn_code;
}
