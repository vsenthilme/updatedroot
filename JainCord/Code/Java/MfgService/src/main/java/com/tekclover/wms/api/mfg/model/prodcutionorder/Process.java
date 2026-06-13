package com.tekclover.wms.api.mfg.model.prodcutionorder;

import lombok.Data;

@Data
public class Process {

    private SortingImpl sorting;

    private SoakingImpl soaking;

    private PeelingImpl peeling;

    private PasteImpl paste;

    private DiceSliceChopImpl diceSliceChop;

    private PowderImpl powder;

    private CookingImpl cooking;
}
