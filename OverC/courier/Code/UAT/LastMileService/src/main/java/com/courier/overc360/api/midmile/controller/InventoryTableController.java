package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.drs.UpdateDrs;
import com.courier.overc360.api.midmile.primary.model.inventorytable.AddInventoryTable;
import com.courier.overc360.api.midmile.primary.model.inventorytable.InventoryTable;
import com.courier.overc360.api.midmile.primary.model.inventorytable.UpdateInventoryTable;
import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.model.inventorytable.FindInventoryTable;
import com.courier.overc360.api.midmile.replica.model.inventorytable.ReplicaInventoryTable;
import com.courier.overc360.api.midmile.service.InventoryTableService;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"InventoryTable"}, value = "InventoryTable Operations related to InventoryTable Controller") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "InventoryTable", description = "Operations related to InventoryTable")})
@RequestMapping("/inventoryTable")
@RestController
public class InventoryTableController {

    @Autowired
    InventoryTableService inventoryTableService;

    /*--------------------------------------PRIMARY----------------------------------------*/

    // Create InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Create InventoryTable") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> postInventoryTable(@Valid @RequestBody AddInventoryTable newInventoryTable, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        InventoryTable createdInventoryTable = inventoryTableService.createInventoryTable(newInventoryTable, loginUserID);
        return new ResponseEntity<>(createdInventoryTable, HttpStatus.OK);
    }

    // Create Drs List
    @ApiOperation(response = InventoryTable.class, value = "Create InventoryTable List") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postInventoryTableList(@Valid @RequestBody List<AddInventoryTable> drsList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<InventoryTable> createdInventoryTable = inventoryTableService.createInventoryTableList(drsList, loginUserID);
        return new ResponseEntity<>(createdInventoryTable, HttpStatus.OK);
    }

    // Update InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Update InventoryTable") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchInventoryTable(@RequestParam String loginUserID, @RequestBody UpdateInventoryTable updateInventoryTable)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        InventoryTable updatedInventoryTable = inventoryTableService.updateInventoryTable(updateInventoryTable, loginUserID);
        return new ResponseEntity<>(updatedInventoryTable, HttpStatus.OK);
    }

    // Update InventoryTable List
    @ApiOperation(response = InventoryTable.class, value = "Update InventoryTable List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchInventoryTableList(@RequestParam String loginUserID, @RequestBody List<InventoryTable> updateInventoryTableList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<InventoryTable> updatedInventoryTable = inventoryTableService.updateInventoryTableList(updateInventoryTableList, loginUserID);
        return new ResponseEntity<>(updatedInventoryTable, HttpStatus.OK);
    }

    // Delete InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Delete InventoryTable") // label for swagger
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInventoryTable(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String customerId, @RequestParam String houseAirwayBill, @RequestParam String loginUserID) {
        inventoryTableService.deleteInventoryTable(languageId, companyId, customerId, houseAirwayBill, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete InventoryTable List
    @ApiOperation(response = InventoryTable.class, value = "Delete InventoryTable") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteInventoryTableList(@RequestBody List<InventoryTable> deleteInventoryTableList, @RequestParam String loginUserID) {
        inventoryTableService.deleteInventoryList(deleteInventoryTableList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All InventoryTable Details
    @ApiOperation(response = ReplicaInventoryTable.class, value = "Get all ReplicaInventoryTable details") // label for swagger
    @GetMapping("/getall")
    public ResponseEntity<?> getAllInventoryTables() {
        List<ReplicaInventoryTable> replicaInventoryTableList = inventoryTableService.getAllInventoryTable();
        return new ResponseEntity<>(replicaInventoryTableList, HttpStatus.OK);
    }

    // Get InventoryTable
    @ApiOperation(response = ReplicaInventoryTable.class, value = "Get a ReplicaInventoryTable") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getInventoryTable(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String customerId, @RequestParam String houseAirwayBill) {

        ReplicaInventoryTable replicaInventoryTable = inventoryTableService.getReplicaInventoryTable(languageId, companyId, customerId, houseAirwayBill);
        return new ResponseEntity<>(replicaInventoryTable, HttpStatus.OK);
    }

    // Find InventoryTable
    @ApiOperation(response = ReplicaInventoryTable.class, value = "Find ReplicaInventoryTable") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findInventoryTable(@RequestBody FindInventoryTable findInventoryTable) throws Exception {
        List<ReplicaInventoryTable> createdInventoryTable = inventoryTableService.findInventoryTable(findInventoryTable);
        return new ResponseEntity<>(createdInventoryTable, HttpStatus.OK);
    }
}
