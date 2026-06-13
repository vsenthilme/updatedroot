package com.tekclover.wms.api.transaction.model.warehouse.ERP;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_outbound")
public class OutboundEntity {

    @Id
    @Column(name = "rowid")
    private Long rowId;

    @Column(name = "system")
    private String system;

//    @Column(name = "order_type")
//    private String orderType;

    @Column(name = "order_ref")
    private String orderRef;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "line_no")
    private Long lineNo;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "order_qty")
    private Double orderQty;

    @Column(name = "uom")
    private String uom;

    @Column(name = "sortno")
    private String sortNo;

    @Column(name = "pieceno")
    private String pieceNo;

    @Column(name = "vchno")
    private String vchNo;

    @Column(name = "processed_status_id")
    private Long processedStatusId = 0L;

}
