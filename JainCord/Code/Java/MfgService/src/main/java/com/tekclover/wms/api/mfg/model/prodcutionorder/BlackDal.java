package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import lombok.Data;

import java.util.List;

@Data
public class BlackDal {

    private ProductionOrder productionOrder;

    private Sorting sorting;

    private Soaking soaking;

    private Peeling peeling;

    private Paste paste;

    private List<OperationConsumption> operationConsumption;

}
