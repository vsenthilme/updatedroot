package com.tekclover.wms.api.transaction.controller;


import com.tekclover.wms.api.transaction.model.warehouse.ERP.InboundEntity;
import com.tekclover.wms.api.transaction.model.warehouse.ERP.OutboundEntity;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.WarehouseApiResponse;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.InboundOrderV2;
import com.tekclover.wms.api.transaction.model.warehouse.outbound.v2.OutboundOrderV2;
import com.tekclover.wms.api.transaction.repository.ERP.InboundEntityRepository;
import com.tekclover.wms.api.transaction.repository.ERP.OutboundEntityRepository;
import com.tekclover.wms.api.transaction.service.ERPService;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"ERP"}, value = "ERP Operations related to ERPController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ERP ",description = "Operations related to ERP ")})
@RequestMapping("/erp")
@RestController
public class ERPController {

    @Autowired
    ERPService erpService;

    @Autowired
    InboundEntityRepository inboundEntityRepository;

    @Autowired
    OutboundEntityRepository outboundEntityRepository;

    @ApiOperation(response = InboundEntity.class, value = "Inbound Create From ERP") // label for swagger
    @PostMapping("/inbound")
    public ResponseEntity<?> postInboundFromERP(@Valid @RequestBody List<InboundEntity> inboundEntity)
            throws IllegalAccessException, InvocationTargetException {
        try {
            // Save InboundEntity
            inboundEntityRepository.saveAll(inboundEntity);
            List<InboundOrderV2> createInbound = erpService.createIbOrder(inboundEntity);
            if (createInbound != null) {
                WarehouseApiResponse response = new WarehouseApiResponse();
                response.setStatusCode("200");
                response.setMessage("Success");
                createInbound.stream().forEach(ib -> {
                    inboundEntityRepository.updateInbound(10L, ib.getRefDocumentNo());
                });
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("inbound order Error: " + inboundEntity);
            e.printStackTrace();
            WarehouseApiResponse response = new WarehouseApiResponse();
            response.setStatusCode("1400");
            response.setMessage("Not Success: " + e.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return null;
    }


    @ApiOperation(response = OutboundEntity.class, value = "Outbound Create From ERP")
    @RequestMapping("/outbound")
    public ResponseEntity<?> postOutboundFromERP(@Valid @RequestBody List<OutboundEntity> outboundEntities)
            throws IllegalAccessException, InvocationTargetException {
        try {
            // Save InboundEntity
            outboundEntityRepository.saveAll(outboundEntities);
            List<OutboundOrderV2> createOutbound = erpService.createOBOrder(outboundEntities);
            if (createOutbound != null) {
                WarehouseApiResponse response = new WarehouseApiResponse();
                response.setStatusCode("200");
                response.setMessage("Success");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("inbound order Error: " + outboundEntities);
            e.printStackTrace();
            WarehouseApiResponse response = new WarehouseApiResponse();
            response.setStatusCode("1400");
            response.setMessage("Not Success: " + e.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return null;
    }


}
