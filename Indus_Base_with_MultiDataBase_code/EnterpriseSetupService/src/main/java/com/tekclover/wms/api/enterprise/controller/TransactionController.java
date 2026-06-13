package com.tekclover.wms.api.enterprise.controller;

import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.WarehouseApiResponse;
import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.v2.ASNV2;
import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.v2.InboundOrderV2;
import com.tekclover.wms.api.enterprise.transaction.service.TransactionWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Transaction"}, value = "Transaction Operations related to WarehouseController")
@SwaggerDefinition(tags = {@Tag(name = "Transaction ", description = "Operations related to Transaction")})
@RequestMapping("/transaction")
@RestController
public class TransactionController {

    @Autowired
    TransactionWarehouseService warehouseService;

    // ASNV2 upload
    @ApiOperation(response = ASNV2.class, value = "Upload Asn V2") // label for swagger
    @PostMapping("/inbound/asn/upload/v2")
    public ResponseEntity<?> postAsnUploadV2(@Valid @RequestBody List<ASNV2> asnv2List) {
        try {
            List<WarehouseApiResponse> responseList = new ArrayList<>();
            String inboundSetNumber = String.valueOf(System.currentTimeMillis());
            asnv2List.stream().forEach(asnv2 -> {
                asnv2.getAsnHeader().setParentProductionOrderNo(inboundSetNumber);
                InboundOrderV2 createdInterWarehouseTransferInV2 =
                        warehouseService.postWarehouseASNV2(asnv2);
                if (createdInterWarehouseTransferInV2 != null) {
                    WarehouseApiResponse response = new WarehouseApiResponse();
                    response.setStatusCode("200");
                    response.setMessage("Success");
                    responseList.add(response);
                }
            });
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            log.info("interWarehouseTransfer order Error: " + e);
            e.printStackTrace();
            WarehouseApiResponse response = new WarehouseApiResponse();
            response.setStatusCode("1400");
            response.setMessage("Not Success: " + e.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
}