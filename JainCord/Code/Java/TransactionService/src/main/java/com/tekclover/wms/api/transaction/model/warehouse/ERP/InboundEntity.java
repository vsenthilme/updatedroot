package com.tekclover.wms.api.transaction.model.warehouse.ERP;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_inbound")
public class InboundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_order_id")
    private Long inboundOrderId;

    @Column(name = "rowid")
    private Long rowId;

    @Column(name = "system")
    private String system;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "order_ref")
    private String orderRef;

    @Column(name = "mrn_date")
    private Date mrnDate;

    @Column(name = "line_no")
    private Long lineNo;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "mrn_qty")
    private Double mrnQty;

    @Column(name = "uom")
    private String uom;

    @Column(name = "sortno")
    private String sortNo;

    @Column(name = "mtr")
    private Double mtr;

    @Column(name = "lotno")
    private String lotNo;

    @Column(name = "pieceno")
    private String pieceNo;

    @Column(name = "gsm")
    private Double gsm;

    @Column(name = "grade")
    private String grade;

    @Column(name = "color")
    private String color;

    @Column(name = "vchno")
    private String vchNo;

    @Column(name = "processed_status_id")
    private Long processedStatusId = 0L;

    @Column(name = "created_on")
    private Date createdOn;
}
