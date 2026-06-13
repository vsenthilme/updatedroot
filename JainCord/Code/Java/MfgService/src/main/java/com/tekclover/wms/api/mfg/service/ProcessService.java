package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.process.Process;
import com.tekclover.wms.api.mfg.model.process.SearchProcess;
import com.tekclover.wms.api.mfg.repository.ProcessRepository;
import com.tekclover.wms.api.mfg.repository.specification.ProcessSpecification;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import com.tekclover.wms.api.mfg.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class ProcessService extends BaseService {

    @Autowired
    ProcessRepository processRepository;

    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param itemCode
     * @param phaseNumber
     * @param bomItem
     * @param batchNumber
     * @return
     */
    public Process getProcess(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber,
                              String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode,
                              String phaseNumber, String bomItem, String batchNumber) {
        Optional<Process> processOperation = processRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo,
                productionOrderLineNo, itemCode, phaseNumber, bomItem, batchNumber, 0L);
        if (processOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ",companyCodeId: " + companyCodeId + ",languageId: "
                    + languageId + ",plantId: " + plantId + ", operationNumber: " + operationNumber + ",receipeId: "
                    + receipeId + ",productionOrderNo: " + productionOrderNo + ",phaseNumber: "
                    + phaseNumber + ",bomItem: " + bomItem + ",batchNumber: "
                    + batchNumber + ",productionOrderLineNo: " + productionOrderLineNo + ",itemCode: " + itemCode + " doesn't exist.");
        }
        return processOperation.get();
    }

    /**
     * Get Bulk Process
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Process> getProcess(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Process> process =
                processRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (process == null || process.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return process;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public List<Process> getProcess(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Process> process =
                processRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        if (process == null || process.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",batchNumber: " + batchNumber +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return process;
    }

    /**
     * @param searchProcess
     * @return
     */
    public Stream<Process> findProcess(SearchProcess searchProcess) {
        try {
            if (searchProcess.getStartCreatedOn() != null && searchProcess.getEndCreatedOn() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(searchProcess.getStartCreatedOn(), searchProcess.getEndCreatedOn());
                searchProcess.setStartCreatedOn(dates[0]);
                searchProcess.setEndCreatedOn(dates[1]);
            }
            log.info("Search Process Initiated -> {}", searchProcess);
            ProcessSpecification spec = new ProcessSpecification(searchProcess);
            Stream<Process> results = processRepository.stream(spec, Process.class);
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newProcessList
     * @param loginUserID
     * @return
     */
    public List<Process> createProcess(List<Process> newProcessList, String loginUserID) {
        try {
            List<Process> createdProcessList = new ArrayList<>();
            for (Process newProcess : newProcessList) {
                Optional<Process> processOperation = processRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                        newProcess.getLanguageId(),
                        newProcess.getCompanyCodeId(),
                        newProcess.getPlantId(),
                        newProcess.getWarehouseId(),
                        newProcess.getOperationNumber(),
                        newProcess.getReceipeId(),
                        newProcess.getProductionOrderNo(),
                        newProcess.getProductionOrderLineNo(),
                        newProcess.getItemCode(),
                        newProcess.getPhaseNumber(),
                        newProcess.getBomItem(),
                        newProcess.getBatchNumber(),
                        0L);
                if (processOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }
                if (newProcess.isUiProcessConfirm() && !newProcess.isBeProcessConfirm()) {
                    log.info("Create Process Initiated -> {}", newProcess);
                    Process dbProcess = new Process();
                    BeanUtils.copyProperties(newProcess, dbProcess, CommonUtils.getNullPropertyNames(newProcess));
                    dbProcess.setStatusId(98L);
                    if (dbProcess.getCompanyCodeId() != null && dbProcess.getPlantId() != null && dbProcess.getLanguageId() != null && dbProcess.getWarehouseId() != null) {
                        description = getDescription(dbProcess.getCompanyCodeId(), dbProcess.getPlantId(), dbProcess.getLanguageId(), dbProcess.getWarehouseId());
                        if (description != null) {
                            dbProcess.setCompanyDescription(description.getCompanyDescription());
                            dbProcess.setPlantDescription(description.getPlantDescription());
                            dbProcess.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbProcess.getStatusId() != null && dbProcess.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbProcess.getStatusId(), dbProcess.getLanguageId());
                        if (statusDescription != null) {
                            dbProcess.setStatusDescription(statusDescription);
                        }
                    }
                    dbProcess.setDeletionIndicator(0L);
                    dbProcess.setCreatedBy(loginUserID);
                    dbProcess.setCreatedOn(new Date());
                    dbProcess.setBeProcessConfirm(true);
                    Process savedProcess = processRepository.save(dbProcess);
                    log.info("Process Created successfully -> {}", savedProcess);
                    createdProcessList.add(savedProcess);
                }
            }
            return createdProcessList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyProcessList
     * @return
     */
    public List<Process> updateProcessBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID, List<Process> modifyProcessList) {
        try {
            List<Process> updatedProcessList = new ArrayList<>();
            for (Process modifyProcess : modifyProcessList) {
                Optional<Process> updateProcess = processRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                        languageId, companyCodeId, plantId, warehouseId, modifyProcess.getOperationNumber(), modifyProcess.getReceipeId(),
                        productionOrderNo, modifyProcess.getProductionOrderLineNo(), modifyProcess.getItemCode(), modifyProcess.getPhaseNumber(),
                        modifyProcess.getBomItem(), modifyProcess.getBatchNumber(), 0L);
                if(updateProcess.isEmpty()) {
                    throw new BadRequestException("The given values: warehouseId: " + warehouseId + ",companyCodeId: " + companyCodeId + ",languageId: "
                            + languageId + ",plantId: " + plantId + ", operationNumber: " + modifyProcess.getOperationNumber() + ",receipeId: "
                            + modifyProcess.getReceipeId() + ",productionOrderNo: " + productionOrderNo + ",phaseNumber: "
                            + modifyProcess.getPhaseNumber() + ",bomItem: " + modifyProcess.getBomItem() + ",batchNumber: "
                            + modifyProcess.getBatchNumber() + ",productionOrderLineNo: " + modifyProcess.getProductionOrderLineNo() + ",itemCode: " + modifyProcess.getItemCode() + " doesn't exist.");
                }
                Process dbProcess = updateProcess.get();
                log.info("Update Process Initiated -> {}", dbProcess);
                BeanUtils.copyProperties(modifyProcess, dbProcess, CommonUtils.getNullPropertyNames(modifyProcess));
                if (dbProcess.getStatusId() != null && dbProcess.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbProcess.getStatusId(), dbProcess.getLanguageId());
                    if (statusDescription != null) {
                        dbProcess.setStatusDescription(statusDescription);
                    }
                }
                dbProcess.setUpdatedBy(loginUserID);
                dbProcess.setUpdatedOn(new Date());
                Process updatedProcess = processRepository.save(dbProcess);
                updatedProcessList.add(updatedProcess);
            }
            return updatedProcessList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @param loginUserID
     */
    public void deleteProcess(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber, String loginUserID) {
        try {
            List<Process> dbProcessList = getProcess(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
            log.info("Delete Process Initiated : {}", dbProcessList.size());
            for(Process dbProcess : dbProcessList) {
                dbProcess.setDeletionIndicator(1L);
                dbProcess.setUpdatedBy(loginUserID);
                dbProcess.setUpdatedOn(new Date());
                processRepository.save(dbProcess);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteProcess(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<Process> dbProcessList = getProcess(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete Process Initiated : {}", dbProcessList.size());
            for(Process dbProcess : dbProcessList) {
                dbProcess.setDeletionIndicator(1L);
                dbProcess.setUpdatedBy(loginUserID);
                dbProcess.setUpdatedOn(new Date());
                processRepository.save(dbProcess);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}