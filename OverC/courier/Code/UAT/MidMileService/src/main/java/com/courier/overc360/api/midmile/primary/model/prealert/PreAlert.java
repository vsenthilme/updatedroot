package com.courier.overc360.api.midmile.primary.model.prealert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblprealert",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_alert",
                        columnNames = {"C_ID", "LANG_ID", "PARTNER_ID", "PARTNER_HOUSE_AIRWAY_BILL", "PARTNER_MASTER_AIRWAY_BILL"}
                )
        }
)
@IdClass(PreAlertCompositeKey.class)
public class PreAlert {

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    //    @Id
    @Column(name = "MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String masterAirwayBill;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Id
    @Column(name = "PARTNER_HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerHouseAirwayBill;

    @Id
    @Column(name = "PARTNER_MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerMasterAirwayBill;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @Column(name = "TOTAL_WEIGHT")
    private Double totalWeight = 0.0;

    @Column(name = "FLIGHT_NO", columnDefinition = "nvarchar(50)")
    private String flightNo;

    @Column(name = "CONSOLE_INDICATOR")
    private Long consoleIndicator;

    @Column(name = "CON_VALUE_LOCAL")
    private Double consignmentValueLocal = 0.0;

    @Column(name = "MANIFEST_INDICATOR")
    private Long manifestIndicator;

    @Column(name = "FLIGHT_NAME", columnDefinition = "nvarchar(50)")
    private String flightName;

    @Column(name = "ESTIMATED_TIME_OF_DEPARTURE")
    private Date estimatedTimeOfDeparture;

    @Column(name = "ESTIMATED_TIME_OF_ARRIVAL")
    private Date estimatedTimeOfArrival;

    @Column(name = "NO_OF_PIECES", columnDefinition = "nvarchar(50)")
    private String noOfPieces;

    @Column(name = "CONSIGNMENT_VALUE")
    private Double consignmentValue = 0.0;

    @Column(name = "EXCHANGE_RATE")
    private Double exchangeRate = 0.0;

    @Column(name = "IATA")
    private Double iata = 0.0;

    @Column(name = "CUSTOMS_INSURANCE")
    private Double customsInsurance = 0.0;

    @Column(name = "DUTY")
    private Double duty = 0.0;

    @Column(name = "ADD_IATA")
    private Double addIata = 0.0;

    @Column(name = "ADD_INSURANCE")
    private Double addInsurance = 0.0;

    @Column(name = "CUSTOMS_VALUE")
    private Double customsValue = 0.0;

    @Column(name = "CALCULATED_TOTAL_DUTY")
    private Double calculatedTotalDuty = 0.0;

    @Column(name = "BAYAN_HV", columnDefinition = "nvarchar(50)")
    private String bayanHv;

    @Column(name = "CURRENCY", columnDefinition = "nvarchar(50)")
    private String currency;

    @Column(name = "DESCRIPTION", columnDefinition = "nvarchar(50)")
    private String description;

    @Column(name = "CONSIGNEE_NAME", columnDefinition = "nvarchar(50)")
    private String consigneeName;

    @Column(name = "SHIPPER", columnDefinition = "nvarchar(50)")
    private String shipper;

    @Column(name = "ORIGIN", columnDefinition = "nvarchar(50)")
    private String origin;

    @Column(name = "ORIGIN_CODE", columnDefinition = "nvarchar(50)")
    private String originCode;

    @Column(name = "CONSIGNEE_PH_NO", columnDefinition = "nvarchar(50)")
    private String consigneePhoneNo;

//    @Column(name = "CON_VALUE_KD", columnDefinition = "nvarchar(50)")
//    private String consignmentValueKd;

    @Column(name = "HS_CODE", columnDefinition = "nvarchar(50)")
    private String hsCode;

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "INCO_TERM", columnDefinition = "nvarchar(50)")
    private String incoTerm;

    @Column(name = "HAWB_TYP", columnDefinition = "nvarchar(50)")
    private String hawbType;

    @Column(name = "HAWB_TYP_ID", columnDefinition = "nvarchar(50)")
    private String hawbTypeId;

    @Column(name = "HAWB_TYP_TXT", columnDefinition = "nvarchar(100)")
    private String hawbTypeDescription;

    @Column(name = "HAWB_TIMESTAMP")
    private Date hawbTimeStamp = new Date();

    @Column(name = "CON_LOCAL_ID", columnDefinition = "nvarchar(50)")
    private String consignmentLocalId;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "HUB_NAME", columnDefinition = "nvarchar(50)")
    private String hubName;

    @Column(name = "SPECIAL_APPROVAL_CHARGE")
    private Double specialApprovalCharge;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "CLEARANCE_CHARGE", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double clearanceCharge;

    @Column(name = "HANDLING_FEES", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double handlingFees;

    @Column(name = "FOOD_APPROVALS")
    private Double foodApprovals;

    @Column(name = "OTHER_APPROVALS")
    private Double otherApprovals;

    @Column(name = "NAS_DELIVERY", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double nasDelivery;

    @Column(name = "GLOBAL", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double global;

    @Column(name = "APPROVAL")
    private Double approval;

    @Column(name = "HANDLING_FORK", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double handlingFork;

    @Column(name = "STAMP_CHARGES", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double stampCharges;

    @Column(name = "ADD_DESTINATION_DETAILS", columnDefinition = "nvarchar(500)")
    private String addDestinationDetails;

    @Column(name = "ADD_ORIGIN_DETAILS", columnDefinition = "nvarchar(500)")
    private String addOriginDetails;

    @Column(name = "TOTAL_APPROVAL")
    private Double totalApproval;

    @Column(name = "INVOICE")
    private Long invoice = 0L;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(500)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(500)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(500)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(500)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(500)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(500)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(500)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(500)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(500)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(500)")
    private String referenceField10;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

    //Report Fields
    @Column(name = "LABOURS", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double labours;

    @Column(name = "OTHER_CHARGES", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double otherCharges;

    @Column(name = "OTHERS", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double others;

    @Column(name = "CUSTOM_DUTY", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double customDuty;

    @Column(name = "SPECIAL_APPROVALS", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double specialApprovals;

    @Column(name = "APPROVALS", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double approvals;

    @Column(name = "TOTAL_COST_PER_SHIPMENT", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double totalCostPerShipment;

    @Column(name = "TOTAL_VALUE_SHIPMENT", columnDefinition = "Decimal(10,3) default '0.000'")
    private Double totalValueShipment;

    @Column(name = "SUB_CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String subCustomerId;

    @Column(name = "SUB_CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String subCustomerName;

    @Column(name = "ORIGIN_FLIGHT_COUNTRY", columnDefinition = "nvarchar(100)")
    private String originFlightCountry;

    @Column(name = "DDP_INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String ddpInvoiceNo;


}