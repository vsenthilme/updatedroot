package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import lombok.Data;

import java.util.List;

@Data
public class GingerPaste {

    private ProductionOrder productionOrder;

    private Sorting sorting;

    private Soaking soaking;

    private Paste paste;

    private List<OperationConsumption> operationConsumption;
}
