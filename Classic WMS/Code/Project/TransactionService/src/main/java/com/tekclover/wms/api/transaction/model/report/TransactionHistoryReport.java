package com.tekclover.wms.api.transaction.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbltransactionhistoryresults")
public class TransactionHistoryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    private String warehouseId;

    private String itemCode;

    @Column(name = "DESCRIPTION")
    private String itemDescription;

    private Double closingStock;

    private Double openingStock;

    private Double inboundQty;

    private Double outboundQty;

    private Double stockAdjustmentQty;

    private Double systemInventory;
<<<<<<< HEAD
    private Double variance;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
