package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import lombok.Data;

import java.util.List;

@Data
public class TomatoPuree {

    private ProductionOrder productionOrder;

    private Paste paste;

    private List<OperationConsumption> operationConsumption;
}
