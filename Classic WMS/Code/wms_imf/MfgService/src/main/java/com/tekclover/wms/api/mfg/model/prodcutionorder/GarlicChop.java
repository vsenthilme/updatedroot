package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import lombok.Data;

import java.util.List;

@Data
public class GarlicChop {

    private Sorting sorting;

    private Soaking soaking;

    private DiceSliceChop diceSliceChop;

    private ProductionOrder productionOrder;

    private List<OperationConsumption> operationConsumption;
}


