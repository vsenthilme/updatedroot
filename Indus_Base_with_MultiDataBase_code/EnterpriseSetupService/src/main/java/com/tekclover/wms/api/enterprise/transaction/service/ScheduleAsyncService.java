package com.tekclover.wms.api.enterprise.transaction.service;

import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.WarehouseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ScheduleAsyncService {

    @Autowired
    OrderProcessService orderProcessService;

    //-------------------------------------------------------------------Inbound---------------------------------------------------------------
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processInboundOrder() throws Exception {

        WarehouseApiResponse inboundOrder = orderProcessService.processInboundOrder();
        return CompletableFuture.completedFuture(inboundOrder);

    }

    //-------------------------------------------------------------------Inbound-Failed-Order-------------------------------------------------------------
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processInboundFailedOrder() throws Exception {

        WarehouseApiResponse inboundFailedOrder = orderProcessService.processInboundFailedOrder();
        return CompletableFuture.completedFuture(inboundFailedOrder);

    }
}