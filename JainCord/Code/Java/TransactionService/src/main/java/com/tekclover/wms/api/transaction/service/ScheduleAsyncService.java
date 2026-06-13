package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.model.warehouse.inbound.WarehouseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ScheduleAsyncService {

    @Autowired
    TransactionService transactionService;

    //-------------------------------------------------------------------Inbound---------------------------------------------------------------
    @Async("asyncTaskExecutor")
<<<<<<< HEAD
    public CompletableFuture<WarehouseApiResponse> processInboundOrder() throws Exception {
=======
    public CompletableFuture<WarehouseApiResponse> processInboundOrder() throws Exception{
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

        WarehouseApiResponse inboundOrder = transactionService.processInboundOrder();
        return CompletableFuture.completedFuture(inboundOrder);

    }

<<<<<<< HEAD
        //-------------------------------------------------------------------Outbound---------------------------------------------------------------
=======
    //-------------------------------------------------------------------Outbound---------------------------------------------------------------
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processOutboundOrder() throws Exception {

        WarehouseApiResponse outboundOrder = transactionService.processOutboundOrder();
        return CompletableFuture.completedFuture(outboundOrder);

    }
<<<<<<< HEAD


=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
    //-------------------------------------------------------------------StockCount---------------------------------------------------------------
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processPerpetualStockCountOrder() throws Exception {

        WarehouseApiResponse perpetualStockCountOrder = transactionService.processPerpetualStockCountOrder();
        return CompletableFuture.completedFuture(perpetualStockCountOrder);

    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processPeriodicStockCountOrder() throws Exception {

        WarehouseApiResponse periodicStockCountOrder = transactionService.processPeriodicStockCountOrder();
        return CompletableFuture.completedFuture(periodicStockCountOrder);

    }

    //-------------------------------------------------------------------StockAdjustment---------------------------------------------------------------
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processStockAdjustmentOrder() throws Exception {

        WarehouseApiResponse stockAdjustmentOrder = transactionService.processStockAdjustmentOrder();
        return CompletableFuture.completedFuture(stockAdjustmentOrder);
    }

    //-------------------------------------------------------------------Inbound-Failed-Order-------------------------------------------------------------
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processInboundFailedOrder() throws Exception {

        WarehouseApiResponse inboundFailedOrder = transactionService.processInboundFailedOrder();
        return CompletableFuture.completedFuture(inboundFailedOrder);

    }

    //-------------------------------------------------------------------Outbound-Failed-Order---------------------------------------------------------------
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> processOutboundFailedOrder() throws Exception {

        WarehouseApiResponse outboundFailedOrder = transactionService.processOutboundFailedOrder();
        return CompletableFuture.completedFuture(outboundFailedOrder);

    }
}