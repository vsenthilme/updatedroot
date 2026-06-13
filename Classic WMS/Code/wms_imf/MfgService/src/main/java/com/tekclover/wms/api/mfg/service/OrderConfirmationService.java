package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.operation.OperationHeader;
import com.tekclover.wms.api.mfg.model.operation.OperationLine;
import com.tekclover.wms.api.mfg.model.prodcutionorder.*;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import com.tekclover.wms.api.mfg.repository.OperationHeaderRepository;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderConfirmationService extends BaseService {

    @Autowired
    SortingService sortingService;

    @Autowired
    SoakingService soakingService;

    @Autowired
    PeelingService peelingService;

    @Autowired
    PasteService pasteService;

    @Autowired
    DiceSliceChopService diceSliceChopService;

    @Autowired
    PowderService powderService;

    @Autowired
    CookingService cookingService;

    @Autowired
    OperationConsumptionService operationConsumptionService;

    @Autowired
    OperationHeaderService operationHeaderService;

    @Autowired
    OperationLineService operationLineService;

    @Autowired
    OperationHeaderRepository operationHeaderRepository;

    /**
     * update BlackDal
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param blackDal
     * @param loginUserID
     */
    public void updateBlackDal(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, BlackDal blackDal, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = blackDal.getSorting();
            sortingList.add(sorting);
            List<Sorting> blackDalSorting = sortingService.updateSorting(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, sortingList);
            log.info("BlackDal Sorting Initiated -> {}", blackDalSorting);
            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = blackDal.getSoaking();
            soakingList.add(soaking);
            List<Soaking> blackDalSoaking = soakingService.updateSoaking(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, soakingList);
            log.info("BlackDal Soaking Initiated -> {}", blackDalSoaking);
            List<Peeling> peelingList = new ArrayList<>();
            Peeling peeling = blackDal.getPeeling();
            peelingList.add(peeling);
            List<Peeling> blackDalPeeling = peelingService.updatePeeling(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, peelingList);
            log.info("BlackDal Peeling Initiated -> {}", blackDalPeeling);
            List<Paste> pasteList = new ArrayList<>();
            Paste paste = blackDal.getPaste();
            pasteList.add(paste);
            List<Paste> blackDalPaste = pasteService.updatePaste(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, pasteList);
            log.info("BlackDal Paste Initiated -> {}", blackDalPaste);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Create BlackDal
     *
     * @param blackDal
     * @param loginUserID
     */
    public void createBlackDal(BlackDal blackDal, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = blackDal.getSorting();
            sorting.setBeProcessConfirm(true);
            sortingList.add(sorting);
            List<Sorting> blackDalSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("BlackDal Sorting Initiated -> {}", blackDalSorting);

            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = blackDal.getSoaking();
            soakingList.add(soaking);
            List<Soaking> blackDalSoaking = soakingService.createSoaking(soakingList, loginUserID);
            log.info("BlackDal Soaking Initiated -> {}", blackDalSoaking);
            List<Peeling> peelingList = new ArrayList<>();
            Peeling peeling = blackDal.getPeeling();
            peelingList.add(peeling);
            List<Peeling> blackDalPeeling = peelingService.createPeeling(peelingList, loginUserID);
            log.info("BlackDal Peeling Initiated -> {}", blackDalPeeling);
            List<Paste> pasteList = new ArrayList<>();
            Paste paste = blackDal.getPaste();
            pasteList.add(paste);
            List<Paste> blackDalPaste = pasteService.createPaste(pasteList, loginUserID);
            log.info("BlackDal Paste Initiated -> {}", blackDalPaste);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(blackDal.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(blackDal.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(blackDal.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(blackDal.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = blackDal.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get BlackDal
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     */
    public BlackDal getBlackDal(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        BlackDal dbBlackDal = new BlackDal();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbBlackDal.setProductionOrder(dbProductionOrder);
        dbBlackDal.setOperationConsumption(operationConsumptionList);

        return dbBlackDal;
    }


    /**
     * update ChanaDal
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param chanaDal
     * @param loginUserID
     */
    public void updateChanaDal(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, ChanaDal chanaDal, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = chanaDal.getSorting();
            sortingList.add(sorting);
            List<Sorting> chanaDalSorting = sortingService.updateSorting(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, sortingList);
            log.info("ChanaDal Sorting Initiated -> {}", chanaDalSorting);
            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = chanaDal.getSoaking();
            soakingList.add(soaking);
            List<Soaking> chanaDalSoaking = soakingService.updateSoaking(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, soakingList);
            log.info("ChanaDal Soaking Initiated -> {}", soakingList);
            List<Peeling> peelingList = new ArrayList<>();
            Peeling peeling = chanaDal.getPeeling();
            peelingList.add(peeling);
            List<Peeling> chanaDalBoiling = peelingService.updatePeeling(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, peelingList);
            log.info("ChanaDal Peeling Initiated -> {}", peelingList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Create ChanaDal
     *
     * @param chanaDal
     * @param loginUserID
     */
    public void createChanaDal(ChanaDal chanaDal, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = chanaDal.getSorting();
            sortingList.add(sorting);
            List<Sorting> chanaDalSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("ChanaDal Sorting Initiated -> {}", chanaDalSorting);
            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = chanaDal.getSoaking();
            soakingList.add(soaking);
            List<Soaking> chanaDalSoaking = soakingService.createSoaking(soakingList, loginUserID);
            log.info("ChanaDal Soaking Initiated -> {}", chanaDalSoaking);
            List<Peeling> peelingList = new ArrayList<>();
            Peeling peeling = chanaDal.getPeeling();
            peelingList.add(peeling);
            List<Peeling> chanaDalBoiling = peelingService.createPeeling(peelingList, loginUserID);
            log.info("ChanaDal Peeling Initiated -> {}", chanaDalBoiling);
            List<Paste> pasteList = new ArrayList<>();
            Paste paste = chanaDal.getPaste();
            pasteList.add(paste);
            List<Paste> chanaDalPaste = pasteService.createPaste(pasteList, loginUserID);
            log.info("ChanaDal Paste Initiated -> {}", chanaDalPaste);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(chanaDal.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(chanaDal.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(chanaDal.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(chanaDal.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = chanaDal.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get ChanaDal
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public ChanaDal getChanaDal(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        ChanaDal dbChanaDal = new ChanaDal();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbChanaDal.setProductionOrder(dbProductionOrder);
        dbChanaDal.setOperationConsumption(operationConsumptionList);

        return dbChanaDal;
    }

    /**
     * Create ChitraRajma
     *
     * @param chitraRajma
     * @param loginUserID
     */
    public void createChitraRajma(ChitraRajma chitraRajma, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = chitraRajma.getSorting();
            sortingList.add(sorting);
            List<Sorting> chitraRajmaSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("ChitraRajma Sorting Initiated -> {}", chitraRajmaSorting);

            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = chitraRajma.getSoaking();
            soakingList.add(soaking);
            List<Soaking> chitraRajmaSoaking = soakingService.createSoaking(soakingList, loginUserID);
            log.info("ChitraRajma Soaking Initiated -> {}", chitraRajmaSoaking);

            List<Peeling> peelingList = new ArrayList<>();
            Peeling peeling = chitraRajma.getPeeling();
            peelingList.add(peeling);
            List<Peeling> chitraRajmaBoiling = peelingService.createPeeling(peelingList, loginUserID);
            log.info("ChitraRajma Peeling Initiated -> {}", chitraRajmaBoiling);

            List<Paste> pasteList = new ArrayList<>();
            Paste paste = chitraRajma.getPaste();
            pasteList.add(paste);
            List<Paste> chitraRajmaPaste = pasteService.createPaste(pasteList, loginUserID);
            log.info("ChitraRajma Paste Initiated -> {}", chitraRajmaPaste);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(chitraRajma.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(chitraRajma.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(chitraRajma.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(chitraRajma.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = chitraRajma.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get ChitraRajma
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public ChitraRajma getChitraRajma(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        ChitraRajma dbChitraRajma = new ChitraRajma();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbChitraRajma.setProductionOrder(dbProductionOrder);
        dbChitraRajma.setOperationConsumption(operationConsumptionList);

        return dbChitraRajma;
    }

    /**
     * Create TomatoPuree
     *
     * @param tomatoPuree
     * @param loginUserID
     */
    public void createTomatoPuree(TomatoPuree tomatoPuree, String loginUserID) {
        try {
            List<Paste> pasteList = new ArrayList<>();
            Paste paste = tomatoPuree.getPaste();
            pasteList.add(paste);
            List<Paste> tomatoPureePaste = pasteService.createPaste(pasteList, loginUserID);
            log.info("TomatoPuree Paste Initiated -> {}", tomatoPureePaste);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(tomatoPuree.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(tomatoPuree.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(tomatoPuree.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(tomatoPuree.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = tomatoPuree.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get TomatoPuree
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public TomatoPuree getTomatoPuree(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        TomatoPuree dbTomatoPuree = new TomatoPuree();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbTomatoPuree.setProductionOrder(dbProductionOrder);
        dbTomatoPuree.setOperationConsumption(operationConsumptionList);

        return dbTomatoPuree;
    }

    /**
     * Create GingerPaste
     *
     * @param gingerPaste
     * @param loginUserID
     */
    public void createGingerPaste(GingerPaste gingerPaste, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = gingerPaste.getSorting();
            sortingList.add(sorting);
            List<Sorting> gingerPasteSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("GingerPaste Sorting Initiated -> {}", gingerPasteSorting);

            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = gingerPaste.getSoaking();
            soakingList.add(soaking);
            List<Soaking> gingerPasteSoaking = soakingService.createSoaking(soakingList, loginUserID);
            log.info("GingerPaste Soaking Initiated -> {}", gingerPasteSoaking);

            List<Paste> pasteList = new ArrayList<>();
            Paste paste = gingerPaste.getPaste();
            pasteList.add(paste);
            List<Paste> gingerPastePaste = pasteService.createPaste(pasteList, loginUserID);
            log.info("GingerPaste Paste Initiated -> {}", gingerPastePaste);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(gingerPaste.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(gingerPaste.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(gingerPaste.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(gingerPaste.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = gingerPaste.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get GingerPaste
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public GingerPaste getGingerPaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        GingerPaste dbGingerPaste = new GingerPaste();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbGingerPaste.setProductionOrder(dbProductionOrder);
        dbGingerPaste.setOperationConsumption(operationConsumptionList);

        return dbGingerPaste;
    }

    /**
     * Create Garlic Paste
     *
     * @param garlicPaste
     * @param loginUserID
     */
    public void createGarlicPaste(GarlicPaste garlicPaste, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = garlicPaste.getSorting();
            sortingList.add(sorting);
            List<Sorting> garlicPasteSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("GarlicPaste Sorting Initiated -> {}", garlicPasteSorting);

            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = garlicPaste.getSoaking();
            soakingList.add(soaking);
            List<Soaking> garlicPasteSoaking = soakingService.createSoaking(soakingList, loginUserID);
            log.info("GarlicPaste Soaking Initiated -> {}", garlicPasteSoaking);

            List<Paste> pasteList = new ArrayList<>();
            Paste paste = garlicPaste.getPaste();
            pasteList.add(paste);
            List<Paste> garlicPastePaste = pasteService.createPaste(pasteList, loginUserID);
            log.info("GarlicPaste Paste Initiated -> {}", garlicPastePaste);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(garlicPaste.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(garlicPaste.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(garlicPaste.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(garlicPaste.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = garlicPaste.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get Garlic Paste
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public GarlicPaste getGarlicPaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        GarlicPaste dbGarlicPaste = new GarlicPaste();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbGarlicPaste.setProductionOrder(dbProductionOrder);
        dbGarlicPaste.setOperationConsumption(operationConsumptionList);

        return dbGarlicPaste;
    }

    /**
     * Create GarlicChop
     *
     * @param garlicChop
     * @param loginUserID
     */
    public void createGarlicChop(GarlicChop garlicChop, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = garlicChop.getSorting();
            sortingList.add(sorting);
            List<Sorting> garlicChopSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("GarlicChop Sorting Initiated -> {}", garlicChopSorting);

            List<Soaking> soakingList = new ArrayList<>();
            Soaking soaking = garlicChop.getSoaking();
            soakingList.add(soaking);
            List<Soaking> garlicChopSoaking = soakingService.createSoaking(soakingList, loginUserID);
            log.info("GarlicChop Soaking Initiated -> {}", garlicChopSoaking);

            List<DiceSliceChop> diceSliceChopList = new ArrayList<>();
            DiceSliceChop diceSliceChop = garlicChop.getDiceSliceChop();
            diceSliceChopList.add(diceSliceChop);
            List<DiceSliceChop> garlicChopDiceSlice = diceSliceChopService.createDiceSliceChopBatch(diceSliceChopList, loginUserID);
            log.info("GarlicChop DiceSliceChop Initiated -> {}", garlicChopDiceSlice);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(garlicChop.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(garlicChop.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(garlicChop.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(garlicChop.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = garlicChop.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get GarlicChop
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public GarlicChop getGarlicChop(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        GarlicChop dbGarlicChop = new GarlicChop();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbGarlicChop.setProductionOrder(dbProductionOrder);
        dbGarlicChop.setOperationConsumption(operationConsumptionList);

        return dbGarlicChop;
    }

    /**
     * Create RoastedKasturiMethi
     *
     * @param roastedKasturiMethi
     * @param loginUserID
     */
    public void createRoastedKasturiMethi(RoastedKasturiMethi roastedKasturiMethi, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = roastedKasturiMethi.getSorting();
            sortingList.add(sorting);
            List<Sorting> roastedkasturiMethiSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("RoastedKasturiMethi Sorting Initiated -> {}", roastedkasturiMethiSorting);

            List<Powder> powderList = new ArrayList<>();
            Powder powder = roastedKasturiMethi.getPowder();
            powderList.add(powder);
            List<Powder> roastedKasturiMethiPowder = powderService.createPowderBatch(powderList, loginUserID);
            log.info("RoastedKasturiMethi Powder Initiated -> {}", roastedKasturiMethiPowder);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(roastedKasturiMethi.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(roastedKasturiMethi.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(roastedKasturiMethi.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(roastedKasturiMethi.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = roastedKasturiMethi.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get RoastedKasturiMethi
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public RoastedKasturiMethi getRoastedKasturiMethi(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        RoastedKasturiMethi dbRoastedKasturiMethi = new RoastedKasturiMethi();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbRoastedKasturiMethi.setProductionOrder(dbProductionOrder);
        dbRoastedKasturiMethi.setOperationConsumption(operationConsumptionList);

        return dbRoastedKasturiMethi;
    }


    /**
     * Create RoastedCuminPowder
     *
     * @param roastedCuminPowder
     * @param loginUserID
     */
    public void createRoastedCuminPowder(RoastedCuminPowder roastedCuminPowder, String loginUserID) {
        try {
            List<Sorting> sortingList = new ArrayList<>();
            Sorting sorting = roastedCuminPowder.getSorting();
            sortingList.add(sorting);
            List<Sorting> roastedCuminPowderSorting = sortingService.createSorting(sortingList, loginUserID);
            log.info("RoastedCuminPowder Sorting Initiated -> {}", roastedCuminPowderSorting);

            List<Powder> powderList = new ArrayList<>();
            Powder powder = roastedCuminPowder.getPowder();
            powderList.add(powder);
            List<Powder> roastedCuminPowderPowder = powderService.createPowderBatch(powderList, loginUserID);
            log.info("RoastedCuminPowder Powder Initiated -> {}", roastedCuminPowderPowder);

            OperationHeader operationHeader = new OperationHeader();
            BeanUtils.copyProperties(roastedCuminPowder.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(roastedCuminPowder.getProductionOrder()));

            operationHeaderService.updateOperationHeader(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationHeader);

            List<OperationLine> operationLineList = new ArrayList<>();

            OperationLine operationLine = new OperationLine();
            BeanUtils.copyProperties(roastedCuminPowder.getProductionOrder().productionLine, operationLine, CommonUtils.getNullPropertyNames(roastedCuminPowder.getProductionOrder().productionLine));
            operationLineList.add(operationLine);

            operationLineService.updateOperationLine(operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(), operationLine.getProductionOrderNo(), operationLine.getCreatedBy(), operationLineList);

            List<OperationConsumption> operationConsumptionList = roastedCuminPowder.getOperationConsumption();
            operationConsumptionService.updateOperationConsumption(operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo(), operationHeader.getCreatedBy(), operationConsumptionList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get RoastedCuminPowder
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public RoastedCuminPowder getRoastedCuminPowder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        RoastedCuminPowder dbRoastedCuminPowder = new RoastedCuminPowder();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        dbRoastedCuminPowder.setProductionOrder(dbProductionOrder);
        dbRoastedCuminPowder.setOperationConsumption(operationConsumptionList);

        return dbRoastedCuminPowder;
    }

    /**
     * Create OrderConfirmation
     *
     * @param orderConfirmation
     * @param loginUserID
     */
    public void createOrderConfirmation(OrderConfirmation orderConfirmation, String loginUserID) {
        try {
            log.info("Order Confirmation: {}", orderConfirmation);
            if(orderConfirmation.getProductionOrder().getProductionLine() != null) {
                String companyCodeId = orderConfirmation.getProductionOrder().getCompanyCodeId();
                String plantId = orderConfirmation.getProductionOrder().getPlantId();
                String languageId = orderConfirmation.getProductionOrder().getLanguageId();
                String warehouseId = orderConfirmation.getProductionOrder().getWarehouseId();
                String productionOrderNo = orderConfirmation.getProductionOrder().getProductionOrderNo();
                String batchNumber = orderConfirmation.getProductionOrder().getProductionLine().getBatchNumber();
                Long productionOrderLineNo = orderConfirmation.getProductionOrder().getProductionLine().getProductionOrderLineNo();

                if (orderConfirmation.getProductionOrder().getProductionLine().getProductionOrderType() != null &&
                        orderConfirmation.getProductionOrder().getProductionLine().getProductionOrderType().equalsIgnoreCase("FG")) {
                    List<OperationHeader> confirmValidation = operationHeaderService.getProductionOrderConfirm(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
                    if (confirmValidation != null && !confirmValidation.isEmpty()) {
                        Long sfgConfirmationCount = confirmValidation.stream().filter(n -> n.getStatusId().equals(98L)).count();
                        Long sfgCount = (long) confirmValidation.size();
                        boolean pass = sfgConfirmationCount.equals(sfgCount);
                        if (!pass) {
                            throw new BadRequestException("Kindly confirm all SFG Production order to confirm this FG Production Order..!");
                        }
                    }
                }

                if (orderConfirmation.getSorting() != null && orderConfirmation.getSorting().isUiProcessConfirm() && !orderConfirmation.getSorting().isBeProcessConfirm()) {
                    Sorting sorting = orderConfirmation.getSorting();
                    if (sorting.getProductionOrderNo() == null || sorting.getProductionOrderNo().isBlank()) {
                        sorting.setProductionOrderNo(productionOrderNo);
                    }
                    if (sorting.getProductionOrderLineNo() == null) {
                        sorting.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (sorting.getBatchNumber() == null || sorting.getBatchNumber().isBlank()) {
                        sorting.setBatchNumber(batchNumber);
                    }
                    sorting.setBeProcessConfirm(true);
                    sorting.setStatusId(98L);
                    Sorting orderConfirmationSorting = sortingService.createSorting(sorting, loginUserID);
                    log.info("OrderConfirmation Sorting created -> {}", orderConfirmationSorting);
                }

                if (orderConfirmation.getSoaking() != null && orderConfirmation.getSoaking().isUiProcessConfirm() && !orderConfirmation.getSoaking().isBeProcessConfirm()) {
                    Soaking soaking = orderConfirmation.getSoaking();
                    if (soaking.getProductionOrderNo() == null || soaking.getProductionOrderNo().isBlank()) {
                        soaking.setProductionOrderNo(productionOrderNo);
                    }
                    if (soaking.getProductionOrderLineNo() == null) {
                        soaking.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (soaking.getBatchNumber() == null || soaking.getBatchNumber().isBlank()) {
                        soaking.setBatchNumber(batchNumber);
                    }
                    soaking.setBeProcessConfirm(true);
                    soaking.setStatusId(98L);
                    Soaking orderConfirmationSoaking = soakingService.createSoaking(soaking, loginUserID);
                    log.info("OrderConfirmation Soaking created -> {}", orderConfirmationSoaking);
                }

                if (orderConfirmation.getPeeling() != null && orderConfirmation.getPeeling().isUiProcessConfirm() && !orderConfirmation.getPeeling().isBeProcessConfirm()) {
                    Peeling peeling = orderConfirmation.getPeeling();
                    if (peeling.getProductionOrderNo() == null || peeling.getProductionOrderNo().isBlank()) {
                        peeling.setProductionOrderNo(productionOrderNo);
                    }
                    if (peeling.getProductionOrderLineNo() == null) {
                        peeling.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (peeling.getBatchNumber() == null || peeling.getBatchNumber().isBlank()) {
                        peeling.setBatchNumber(batchNumber);
                    }
                    peeling.setBeProcessConfirm(true);
                    peeling.setStatusId(98L);
                    Peeling orderConfirmationPeeling = peelingService.createPeeling(peeling, loginUserID);
                    log.info("OrderConfirmation Peeling created -> {}", orderConfirmationPeeling);
                }

                if (orderConfirmation.getPaste() != null && orderConfirmation.getPaste().isUiProcessConfirm() && !orderConfirmation.getPaste().isBeProcessConfirm()) {
                    Paste paste = orderConfirmation.getPaste();
                    if (paste.getProductionOrderNo() == null || paste.getProductionOrderNo().isBlank()) {
                        paste.setProductionOrderNo(productionOrderNo);
                    }
                    if (paste.getProductionOrderLineNo() == null) {
                        paste.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (paste.getBatchNumber() == null || paste.getBatchNumber().isBlank()) {
                        paste.setBatchNumber(batchNumber);
                    }
                    paste.setBeProcessConfirm(true);
                    paste.setStatusId(98L);
                    Paste orderConfirmationPaste = pasteService.createPaste(paste, loginUserID);
                    log.info("OrderConfirmationPaste Paste created -> {}", orderConfirmationPaste);
                }

                if (orderConfirmation.getDiceSliceChop() != null && orderConfirmation.getDiceSliceChop().isUiProcessConfirm() && !orderConfirmation.getDiceSliceChop().isBeProcessConfirm()) {
                    DiceSliceChop diceSliceChop = orderConfirmation.getDiceSliceChop();
                    if (diceSliceChop.getProductionOrderNo() == null || diceSliceChop.getProductionOrderNo().isBlank()) {
                        diceSliceChop.setProductionOrderNo(productionOrderNo);
                    }
                    if (diceSliceChop.getProductionOrderLineNo() == null) {
                        diceSliceChop.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (diceSliceChop.getBatchNumber() == null || diceSliceChop.getBatchNumber().isBlank()) {
                        diceSliceChop.setBatchNumber(batchNumber);
                    }
                    diceSliceChop.setBeProcessConfirm(true);
                    diceSliceChop.setStatusId(98L);
                    DiceSliceChop orderConfirmationDiceSliceChop = diceSliceChopService.createDiceSliceChopBatch(diceSliceChop, loginUserID);
                    log.info("OrderConfirmation DiceSliceChop created -> {}", orderConfirmationDiceSliceChop);
                }

                if (orderConfirmation.getPowder() != null && orderConfirmation.getPowder().isUiProcessConfirm() && !orderConfirmation.getPowder().isBeProcessConfirm()) {
                    Powder powder = orderConfirmation.getPowder();
                    if (powder.getProductionOrderNo() == null || powder.getProductionOrderNo().isBlank()) {
                        powder.setProductionOrderNo(productionOrderNo);
                    }
                    if (powder.getProductionOrderLineNo() == null) {
                        powder.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (powder.getBatchNumber() == null || powder.getBatchNumber().isBlank()) {
                        powder.setBatchNumber(batchNumber);
                    }
                    powder.setBeProcessConfirm(true);
                    powder.setStatusId(98L);
                    Powder orderConfirmationPowder = powderService.createPowder(powder, loginUserID);
                    log.info("OrderConfirmation Powder created -> {}", orderConfirmationPowder);
                }

                if (orderConfirmation.getCooking() != null && orderConfirmation.getCooking().isUiProcessConfirm() && !orderConfirmation.getCooking().isBeProcessConfirm()) {
                    Cooking cooking = orderConfirmation.getCooking();
                    if (cooking.getProductionOrderNo() == null || cooking.getProductionOrderNo().isBlank()) {
                        cooking.setProductionOrderNo(productionOrderNo);
                    }
                    if (cooking.getProductionOrderLineNo() == null) {
                        cooking.setProductionOrderLineNo(productionOrderLineNo);
                    }
                    if (cooking.getBatchNumber() == null || cooking.getBatchNumber().isBlank()) {
                        cooking.setBatchNumber(batchNumber);
                    }
                    cooking.setBeProcessConfirm(true);
                    cooking.setStatusId(98L);
                    Cooking orderConfirmationCooking = cookingService.createCooking(cooking, loginUserID);
                    log.info("OrderConfirmation Cooking created -> {}", orderConfirmationCooking);
                }

                OperationLine operationLine = orderConfirmation.getProductionOrder().getProductionLine();
                if (operationLine != null) {
                    log.info("Actual Qty for IB OrderCreate : {}", operationLine.getActualQuantity());

                    List<OperationConsumption> operationConsumptionList = orderConfirmation.getOperationConsumption();
                    if (operationConsumptionList != null && !operationConsumptionList.isEmpty()) {
                        operationConsumptionList.stream().filter(n -> n.isUiProcessConfirm() && !n.isBeProcessConfirm()).forEach(n -> {
                            n.setOrderConfirmedBy(loginUserID);
                            n.setOrderConfirmedOn(new Date());
                            n.setStatusId(98L);
                            n.setBeProcessConfirm(true);
                        });

                        operationConsumptionService.updateOperationConsumption(
                                operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(),
                                operationLine.getProductionOrderNo(), loginUserID, operationConsumptionList);
                    }

                    OperationHeader operationHeader = new OperationHeader();
                    BeanUtils.copyProperties(orderConfirmation.getProductionOrder(), operationHeader, CommonUtils.getNullPropertyNames(orderConfirmation.getProductionOrder()));

                    log.info("OperationId, Phase Id : {}, {}", operationLine.getOperationNumber(), operationHeader.getReferenceField1());
                    log.info("Final Phase Id : {}", operationHeader.getReferenceField1());
                    if (operationLine.getActualQuantity() != null) {
                        log.info("Actual Qty, Total Order Qty : {}, {}", operationLine.getActualQuantity(), operationHeader.getTotalOrderQuantity());
//                        if (operationLine.getActualQuantity() > operationHeader.getTotalOrderQuantity()) {
//                            throw new BadRequestException("Actual Quantity is greater than Total Order Quantity..!");
//                        }
                        if (operationHeader.getReferenceField1() != null && !operationHeader.getReferenceField1().isBlank()) {
                            String finalPhaseId = operationHeader.getReferenceField1();
                            String dbFinalPhaseId = operationHeaderRepository.getPhaseId(
                                    operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(),
                                    operationLine.getWarehouseId(), operationLine.getOperationNumber());
                            boolean conditionPass = finalPhaseId.equalsIgnoreCase(dbFinalPhaseId);
                            log.info("conditionPass,FinalPhaseId,dbFinalPhaseId : {}, {}, {}", conditionPass, finalPhaseId, dbFinalPhaseId);
                            if (conditionPass) {
                                operationLineService.createInboundOrder(operationLine);

                                operationHeader.setStatusId(98L);
                                operationHeader.setOrderConfirmedBy(loginUserID);
                                operationHeader.setOrderConfirmedOn(new Date());
                                operationHeaderService.updateOperationHeader(
                                        operationHeader.getCompanyCodeId(), operationHeader.getPlantId(), operationHeader.getLanguageId(), operationHeader.getWarehouseId(),
                                        operationHeader.getProductionOrderNo(), loginUserID, operationHeader);

                                List<OperationLine> operationLineList = new ArrayList<>();
                                operationLine.setOrderConfirmedBy(loginUserID);
                                operationLine.setOrderConfirmedOn(new Date());
                                operationLine.setStatusId(98L);
                                operationLineList.add(operationLine);

                                operationLineService.updateOperationLine(
                                        operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(),
                                        operationLine.getProductionOrderNo(), loginUserID, operationLineList);

                                if (operationConsumptionList != null && !operationConsumptionList.isEmpty()) {
                                    operationConsumptionList.stream().filter(n -> n.getStatusId().equals(97L)).forEach(n -> {
                                        n.setOrderConfirmedBy(loginUserID);
                                        n.setOrderConfirmedOn(new Date());
                                        n.setStatusId(98L);
                                        n.setBeProcessConfirm(true);
                                    });

                                    operationConsumptionService.updateOperationConsumption(
                                            operationLine.getCompanyCodeId(), operationLine.getPlantId(), operationLine.getLanguageId(), operationLine.getWarehouseId(),
                                            operationLine.getProductionOrderNo(), loginUserID, operationConsumptionList);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Get OrderConfirmation
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public OrderConfirmation getOrderConfirmation(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        //Single Line
        OrderConfirmation dbOrderConfirmation = new OrderConfirmation();

        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));

        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbProductionOrder.setProductionLine(operationLine);

        //Lines
        List<OperationConsumption> operationConsumptionList = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setProductionOrder(dbProductionOrder);
        dbOrderConfirmation.setOperationConsumption(operationConsumptionList);

        Sorting sorting = sortingService.getSorting(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setSorting(sorting);

        Soaking soaking = soakingService.getSoaking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setSoaking(soaking);

        Peeling peeling = peelingService.getPeeling(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setPeeling(peeling);

        Paste paste = pasteService.getPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setPaste(paste);

        DiceSliceChop diceSliceChop = diceSliceChopService.getDiceSliceChop(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setDiceSliceChop(diceSliceChop);

        Powder powder = powderService.getPowder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setPowder(powder);

        Cooking cooking = cookingService.getCooking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        dbOrderConfirmation.setCooking(cooking);

        return dbOrderConfirmation;
    }

}