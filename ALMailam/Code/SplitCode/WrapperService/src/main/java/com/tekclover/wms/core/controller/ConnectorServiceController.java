package com.tekclover.wms.core.controller;

import com.tekclover.wms.core.model.Connector.*;
import com.tekclover.wms.core.model.auth.AuthToken;
import com.tekclover.wms.core.model.transaction.IntegrationLog;
import com.tekclover.wms.core.service.AuthTokenService;
import com.tekclover.wms.core.service.ConnectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("wms-connector-service")
@Api(tags = {"Connector Service"}, value = "Connector Service Operations") //label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operation Related to Connector Service Modules")})
public class ConnectorServiceController {

    @Autowired
    ConnectorService connectorService;

    AuthTokenService authTokenService;

    private String getAuthToken() {
        AuthToken authTokenForConnectorService = authTokenService.getConnectorServiceAuthToken();
        return authTokenForConnectorService.getAccess_token();
    }

    //======================================Integration Log=================================================

    //GET ALL INTEGRATION LOG
    @ApiOperation(response = IntegrationLog.class, value = "Get All Integration Log Details")
    @GetMapping("/integrationlog")
    public ResponseEntity<?> getAllIntegrationLog() {
        IntegrationLog[] integrationLogs = connectorService.getAllIntegrationLog(getAuthToken());
        return new ResponseEntity<>(integrationLogs, HttpStatus.OK);
    }

    //FindSupplierInvoice
    @ApiOperation(response = SupplierInvoiceHeader[].class, value = "Find SupplierInvoiceHeader")//label for swagger
    @PostMapping("/findSupplierInvoiceHeader")
    public ResponseEntity<?> findSupplierInvoiceHeader(@RequestBody FindSupplierInvoiceHeader searchSupplierInvoiceHeader) {
        SupplierInvoiceHeader[] supplierInvoiceHeader = connectorService.findSupplierInvoiceHeader(searchSupplierInvoiceHeader, getAuthToken());
        return new ResponseEntity<>(supplierInvoiceHeader, HttpStatus.OK);
    }

    @ApiOperation(response = StockReceiptHeader[].class, value = "Find StockReceiptHeader") //label for swagger
    @PostMapping("/findStockReceiptHeader")
    public ResponseEntity<?> findStockReceiptHeader(@RequestBody SearchStockReceiptHeader searchStockReceiptHeader) {
        StockReceiptHeader[] supplierStockReceiptHeader = connectorService.findStockReceiptHeader(searchStockReceiptHeader, getAuthToken());
        return new ResponseEntity<>(supplierStockReceiptHeader, HttpStatus.OK);
    }

    @ApiOperation(response = TransferInHeader[].class, value = "Find B2BTransferInHeader")//label for swagger
    @PostMapping("/findB2BTransferInHeader")
    public ResponseEntity<?> findB2BTransferInHeader(@RequestBody SearchTransferInHeader searchTransferInHeader) {
        TransferInHeader[] transferInHeader = connectorService.findB2BTransferInHeader(searchTransferInHeader, getAuthToken());
        return new ResponseEntity<>(transferInHeader, HttpStatus.OK);
    }

    @ApiOperation(response = TransferInHeader[].class, value = "Find InterWareHouseTransferInHeader") //label for swagger
    @PostMapping("/findInterWareHouseTransferInHeader")
    public ResponseEntity<?> findInterWareHouseTransferInHeader(@RequestBody SearchTransferInHeader searchTransferInHeader) {
        TransferInHeader[] transferInHeader = connectorService.findInterWareHouseTransferInHeader(searchTransferInHeader, getAuthToken());
        return new ResponseEntity<>(transferInHeader, HttpStatus.OK);
    }

    // Find SalesReturnHeader
    @ApiOperation(response = SalesReturnHeader[].class, value = "Find SalesReturnHeader") //label for swagger
    @PostMapping("/findSalesReturnHeader")
    public ResponseEntity<?> findSalesReturnHeader(@RequestBody FindSalesReturnHeader findSalesReturnHeader) {
        SalesReturnHeader[] salesReturnHeader = connectorService.findSalesReturnHeader(findSalesReturnHeader, getAuthToken());
        return new ResponseEntity<>(salesReturnHeader, HttpStatus.OK);
    }

    // Find StockAdjustment
    @ApiOperation(response = StockAdjustment[].class, value = "Find StockAdjustment") //label for swagger
    @PostMapping("/findStockAdjustment")
    public ResponseEntity<?> findStockAdjustment(@RequestBody FindStockAdjustment findStockAdjustment) {
        StockAdjustment[] stockAdjustment = connectorService.findStockAdjustment(findStockAdjustment, getAuthToken());
        return new ResponseEntity<>(stockAdjustment, HttpStatus.OK);
    }

    // Find PickListHeader
    @ApiOperation(response = PickListHeader[].class, value = "Find PickListHeader") //label for swagger
    @PostMapping("/findPickListHeader")
    public ResponseEntity<?> findPickListHeader(@RequestBody FindPickListHeader findPickListHeader) {
        PickListHeader[] pickListHeader = connectorService.findPickListHeader(findPickListHeader, getAuthToken());
        return new ResponseEntity<>(pickListHeader, HttpStatus.OK);
    }

    // Find PurchaseReturnHeader
    @ApiOperation(response = PurchaseReturnHeader[].class, value = "Find PurchaseReturnHeader") //label for swagger
    @PostMapping("/findPurchaseReturnHeader")
    public ResponseEntity<?> findPurchaseReturnHeader(@RequestBody FindPurchaseReturnHeader findPurchaseReturnHeader) {
        PurchaseReturnHeader[] purchaseReturnHeader = connectorService.findPurchaseReturnHeader(findPurchaseReturnHeader, getAuthToken());
        return new ResponseEntity<>(purchaseReturnHeader, HttpStatus.OK);
    }

    // Find SalesInvoice
    @ApiOperation(response = SalesInvoice[].class, value = "Find SalesInvoice") //label for swagger
    @PostMapping("/findSalesInvoice")
    public ResponseEntity<?> findSalesInvoice(@RequestBody FindSalesInvoice findSalesInvoice) {
        SalesInvoice[] salesInvoice = connectorService.findSalesInvoice(findSalesInvoice, getAuthToken());
        return new ResponseEntity<>(salesInvoice, HttpStatus.OK);
    }

    // Find InterWarehouseTransferOut
    @ApiOperation(response = TransferOutHeader[].class, value = "Find InterWarehouseTransferOut") //label for swagger
    @PostMapping("/findInterWarehouseTransferOut")
    public ResponseEntity<?> searchInterWarehouseTransferOut(@RequestBody FindTransferOutHeader findTransferOutHeader) {
        TransferOutHeader[] interWarehouseTransferOut = connectorService.findInterWarehouseTransferOut(findTransferOutHeader, getAuthToken());
        return new ResponseEntity<>(interWarehouseTransferOut, HttpStatus.OK);
    }


    // Find ShipmentOrder
    @ApiOperation(response = TransferOutHeader[].class, value = "Find ShipmentOrder") //label for swagger
    @PostMapping("/findShipmentOrder")
    public ResponseEntity<?> searchShipmentOrder(@RequestBody FindTransferOutHeader findTransferOutHeader) {
        TransferOutHeader[] shipmentOrder = connectorService.findShipmentOrder(findTransferOutHeader, getAuthToken());
        return new ResponseEntity<>(shipmentOrder, HttpStatus.OK);
    }

    // Find ItemMaster
    @ApiOperation(response = ItemMaster[].class, value = "Find ItemMaster") //label for swagger
    @PostMapping("/findItemMaster")
    public ResponseEntity<?> searchItemMaster(@RequestBody FindItemMaster findItemMaster) {
        ItemMaster[] itemMaster = connectorService.findItemMaster(findItemMaster, getAuthToken());
        return new ResponseEntity<>(itemMaster, HttpStatus.OK);
    }

    // Find CustomerMaster
    @ApiOperation(response = CustomerMaster[].class, value = "Find CustomerMaster") //label for swagger
    @PostMapping("/findCustomerMaster")
    public ResponseEntity<?> searchCustomerMaster(@RequestBody FindCustomerMaster findCustomerMaster) {
        CustomerMaster[] customerMaster = connectorService.findCustomerMaster(findCustomerMaster, getAuthToken());
        return new ResponseEntity<>(customerMaster, HttpStatus.OK);
    }

    // Find PerpetualHeader
    @ApiOperation(response = PerpetualHeader[].class, value = "Find PerpetualHeader") //label for swagger
    @PostMapping("/findPerpetualHeader")
    public ResponseEntity<?> searchPerpetualHeader(@RequestBody FindPerpetualHeader findPerpetualHeader) {
        PerpetualHeader[] perpetualHeaders = connectorService.findPerpetualHeader(findPerpetualHeader, getAuthToken());
        return new ResponseEntity<>(perpetualHeaders, HttpStatus.OK);
    }

    // Find PeriodicHeader
    @ApiOperation(response = PeriodicHeader[].class, value = "Find PeriodicHeader") //label for swagger
    @PostMapping("/findPeriodicHeader")
    public ResponseEntity<?> searchPeriodicHeader(@RequestBody FindPeriodicHeader findPeriodicHeader) {
        PeriodicHeader[] periodicHeader = connectorService.findPeriodicHeader(findPeriodicHeader, getAuthToken());
        return new ResponseEntity<>(periodicHeader, HttpStatus.OK);
    }

    //Find InterWarehouseTransferOutLine
    @ApiOperation(response = TransferOutLine[].class, value = "Find InterWarehouseTransferOutLine") //label for swagger
    @PostMapping("/findInterWarehouseTransferOutLine")
    public ResponseEntity<?> searchInterWarehouseTransferOutLine(@RequestBody FindTransferOutLine findTransferOutLine) {
        TransferOutLine[] interWarehouseTransferOutLines = connectorService.findInterWarehouseTransferOutLine(findTransferOutLine, getAuthToken());
        return new ResponseEntity<>(interWarehouseTransferOutLines, HttpStatus.OK);
    }


    @ApiOperation(response = TransferOutLine[].class, value = "Find ShipmentOrderLine") //label for swagger
    @PostMapping("/findShipmentOrderLine")
    public ResponseEntity<?> searchShipmentOrderLine(@RequestBody FindTransferOutLine findTransferOutLine) {
        TransferOutLine[] ShipmentOrderLines = connectorService.findShipmentOrderLine(findTransferOutLine, getAuthToken());
        return new ResponseEntity<>(ShipmentOrderLines, HttpStatus.OK);
    }

    @ApiOperation(response = TransferInLine[].class, value = "Find InterWareHouseTransferInLine")//label for swagger
    @PostMapping("/findInterWareHouseTransferInLine")
    public ResponseEntity<?> findInterWareHouseTransferInLine(@RequestBody SearchTransferInLine searchTransferInLine) {
        TransferInLine[] transferInLine = connectorService.findInterWareHouseTransferInLine(searchTransferInLine, getAuthToken());
        return new ResponseEntity<>(transferInLine, HttpStatus.OK);
    }

    @ApiOperation(response = TransferInLine[].class, value = "Find B2BTransferInLine")//label for swagger
    @PostMapping("/findB2BTransferInLine")
    public ResponseEntity<?> findB2BTransferInLine(@RequestBody SearchTransferInLine searchTransferInLine) {
        TransferInLine[] transferInLine = connectorService.findInterWareHouseTransferInLine(searchTransferInLine, getAuthToken());
        return new ResponseEntity<>(transferInLine, HttpStatus.OK);
    }

    @ApiOperation(response = SupplierInvoiceLine[].class, value = "Find SupplierInvoiceLine")//label for swagger
    @PostMapping("/findSupplierInvoiceLine")
    public ResponseEntity<?> findSupplierInvoiceLine(@RequestBody SearchSupplierInvoiceLine searchSupplierInvoiceLine) {
        SupplierInvoiceLine[] supplierInvoiceLine = connectorService.findSupplierInvoiceLine(searchSupplierInvoiceLine, getAuthToken());
        return new ResponseEntity<>(supplierInvoiceLine, HttpStatus.OK);
    }

    @ApiOperation(response = StockReceiptLine[].class, value = "Find StockReceiptLine")//label for swagger
    @PostMapping("/findStockReceiptLine")
    public ResponseEntity<?> findStockReceiptLine(@RequestBody SearchStockReceiptLine searchStockReceiptLine) {
        StockReceiptLine[] stockReceiptLine = connectorService.findStockReceiptLine(searchStockReceiptLine, getAuthToken());
        return new ResponseEntity<>(stockReceiptLine, HttpStatus.OK);
    }

    @ApiOperation(response = SalesReturnLine[].class, value = "Find SalesReturnLine")//label for swagger
    @PostMapping("/findSalesReturnLine")
    public ResponseEntity<?> findSalesReturnLine(@RequestBody FindSalesReturnLine findSalesReturnLine) {
        SalesReturnLine[] salesReturnLine = connectorService.findSalesReturnLine(findSalesReturnLine, getAuthToken());
        return new ResponseEntity<>(salesReturnLine, HttpStatus.OK);
    }

    @ApiOperation(response = PurchaseReturnLine[].class, value = "Find PurchaseReturnLine")//label for swagger
    @PostMapping("/findPurchaseReturnLine")
    public ResponseEntity<?> findPurchaseReturnLine(@RequestBody FindPurchaseReturnLine findPurchaseReturnLine) {
        PurchaseReturnLine[] purchaseReturnLine = connectorService.findPurchaseReturnLine(findPurchaseReturnLine, getAuthToken());
        return new ResponseEntity<>(purchaseReturnLine, HttpStatus.OK);
    }

    @ApiOperation(response = PickListLine[].class, value = "Find PickListLine")//label for swagger
    @PostMapping("/findPickListLine")
    public ResponseEntity<?> findPickListLine(@RequestBody FindPickListLine findPickListLine) {
        PickListLine[] pickListLine = connectorService.findPickListLine(findPickListLine, getAuthToken());
        return new ResponseEntity<>(pickListLine, HttpStatus.OK);
    }
    @ApiOperation(response = PeriodicLine[].class, value = "Find PeriodicLine")//label for swagger
    @PostMapping("/findPeriodicLine")
    public ResponseEntity<?> findPeriodicLine(@RequestBody FindPeriodicLine findPeriodicLine) {
        PeriodicLine[] periodicLine = connectorService.findPeriodicLine(findPeriodicLine, getAuthToken());
        return new ResponseEntity<>(periodicLine, HttpStatus.OK);
    }
    @ApiOperation(response = PerpetualLine[].class, value = "Find PerpetualLine")//label for swagger
    @PostMapping("/findPerpetualLine")
    public ResponseEntity<?> findPerpetualLine(@RequestBody FindPerpetualLine findPerpetualLine) {
        PerpetualLine[] perpetualLine = connectorService.findPerpetualLine(findPerpetualLine, getAuthToken());
        return new ResponseEntity<>(perpetualLine, HttpStatus.OK);
    }
}
