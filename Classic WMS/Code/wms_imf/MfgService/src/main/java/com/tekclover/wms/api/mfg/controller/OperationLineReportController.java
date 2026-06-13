package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.cooking.SearchCooking;
import com.tekclover.wms.api.mfg.model.diceslicechop.SearchDiceSliceChop;
import com.tekclover.wms.api.mfg.model.paste.SearchPaste;
import com.tekclover.wms.api.mfg.model.peeling.SearchPeeling;
import com.tekclover.wms.api.mfg.model.powder.SearchPowder;
import com.tekclover.wms.api.mfg.model.prodcutionorder.*;
import com.tekclover.wms.api.mfg.model.soaking.SearchSoaking;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.SearchSorting;
import com.tekclover.wms.api.mfg.service.OperationLineReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@Api(tags = {"OperationLineReport"}, value = "OperationLineReport Operations related to OperationLineReportController")
@SwaggerDefinition(tags = {@Tag(name = "OperationLineReport", description = "Operations Related to OperationLineReport")})
@RequestMapping("/operationLineReport")
@RestController
public class OperationLineReportController {

    @Autowired
    OperationLineReportService operationLineReportService;

    @ApiOperation(response = OperationLineImpl.class, value = "Get an OperationLineReport")
    @PostMapping("/findOperationLineReport")
    public List<OperationLineReport> findOperationLineReport(@RequestBody SearchOperationLineReport searchOperationLine) throws Exception {
        return operationLineReportService.findOperationLineSQL(searchOperationLine);
    }

    @ApiOperation(response = ProcessReport.class, value = "Get an OperationLineReport - Process")
    @PostMapping("/findOperationLineProcessReport")
    public ProcessReport findOperationLineProcessReport(@RequestBody SearchOperationLineReportProcess searchOperationLine) throws Exception {
        return operationLineReportService.findProcess(searchOperationLine);
    }

//    @ApiOperation(response = OperationLineImpl.class, value = "Get an OperationLineV2Report")
//    @PostMapping("/findOperationLineV2Report")
//    public List<OperationLineReport> findOperationLineV2Report(@RequestBody SearchOperationLineReport searchOperationLine) throws Exception {
//        return operationLineReportService.findOperationLineV2SQL(searchOperationLine);
//    }

//    @ApiOperation(response = SortingImpl.class, value = "Get an SortingReport")
//    @PostMapping("/findSortingReport")
//    public SortingImpl findSorting(@RequestBody SearchSorting searchSorting) throws Exception {
//        return operationLineReportService.findSortingSQL(searchSorting);
//    }
//
//    @ApiOperation(response = Soaking.class, value = "Get an SoakingReport")
//    @PostMapping("/findSoakingReport")
//    public SoakingImpl findSorting(@RequestBody SearchSoaking searchSoaking) throws Exception {
//        return operationLineReportService.findSoakingSQL(searchSoaking);
//    }
//
//    @ApiOperation(response = PeelingImpl.class, value = "Get an PeelingReport")
//    @PostMapping("/findPeelingReport")
//    public PeelingImpl findPeeling(@RequestBody SearchPeeling searchPeeling) throws Exception {
//        return operationLineReportService.findPeelingSQL(searchPeeling);
//    }
//
//    @ApiOperation(response = PasteImpl.class, value = "Get an PasteReport")
//    @PostMapping("/findPasteReport")
//    public PasteImpl findPaste(@RequestBody SearchPaste searchPaste) throws Exception {
//        return operationLineReportService.findPasteSQL(searchPaste);
//    }
//
//    @ApiOperation(response = PowderImpl.class, value = "Get a PowderReport")
//    @PostMapping("/findPowderReport")
//    public PowderImpl findPowder(@RequestBody SearchPowder searchPowder) throws Exception {
//        return operationLineReportService.findPowderSQL(searchPowder);
//    }
//
//    @ApiOperation(response = DiceSliceChopImpl.class, value = "Get a DiceSliceChopReport")
//    @PostMapping("/findDiceSliceChopReport")
//    public DiceSliceChopImpl findDiceSliceChop(@RequestBody SearchDiceSliceChop searchDiceSliceChop) throws Exception {
//        return operationLineReportService.findDiceSliceChopSQL(searchDiceSliceChop);
//    }
//
//    @ApiOperation(response = CookingImpl.class, value = "Get a CookingReport")
//    @PostMapping("/findCookingReport")
//    public CookingImpl findCooking(@RequestBody SearchCooking searchCooking) throws Exception {
//        return operationLineReportService.findCookingSQL(searchCooking);
//    }
}