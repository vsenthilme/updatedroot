package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.operation.OperationLine;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import lombok.Data;

import java.util.List;

@Data
public class OrderConfirmationReport {

    private OperationLine operationLine;

    private List<OperationConsumption> operationConsumption;

    private Sorting sorting;

    private Soaking soaking;

    private Peeling peeling;

    private Paste paste;

    private DiceSliceChop diceSliceChop;

    private Powder powder;

    private Cooking cooking;
}
