package com.tekclover.wms.api.mfg.model.prodcutionorder;

import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import lombok.Data;

import java.util.List;

@Data
public class ProcessReport {

    private List<Sorting> sorting;

    private List<Soaking> soaking;

    private List<Peeling> peeling;

    private List<Paste> paste;

    private List<DiceSliceChop> diceSliceChop;

    private List<Powder> powder;

    private List<Cooking> cooking;
}