package com.tekclover.wms.api.transaction.model.inbound.gr.v2;

import com.tekclover.wms.api.transaction.model.inbound.gr.AddGrLine;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddGrLineV2 extends AddGrLine {

    private Double threePlCbm;
    private String threePlUom;
    private String threePlBillStatus;
    private Double threePlLength;
    private Double threePlWidth;
    private Double threePlHeight;
    private Double cbmQuantity;
    private String interimStorageBin;
    private String companyDescription;
    private String plantDescription;
    private String warehouseDescription;
}
