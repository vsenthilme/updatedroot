package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import lombok.Data;

import java.util.List;

@Data
public class RoastedKasturiMethi {

    private ProductionOrder productionOrder;

    private List<OperationConsumption> operationConsumption;

    private Sorting sorting;

    private Powder powder;
}
