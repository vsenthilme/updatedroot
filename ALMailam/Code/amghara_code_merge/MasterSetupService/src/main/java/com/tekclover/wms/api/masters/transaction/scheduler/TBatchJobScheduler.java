package com.tekclover.wms.api.masters.transaction.scheduler;

import com.tekclover.wms.api.masters.scheduler.BatchJobScheduler;
import com.tekclover.wms.api.masters.transaction.model.warehouse.inbound.WarehouseApiResponse;
import com.tekclover.wms.api.masters.transaction.service.ScheduleAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class TBatchJobScheduler {

    @Autowired
    ScheduleAsyncService scheduleAsyncService;

    @Autowired
    BatchJobScheduler batchJobScheduler;

    //-------------------------------------------------------------------------------------------

    @Scheduled(fixedDelay = 20000)
    public void scheduleJob() throws InterruptedException, InvocationTargetException, IllegalAccessException, Exception {

        CompletableFuture<WarehouseApiResponse> outboundOrder = scheduleAsyncService.processOutboundOrder();
//        batchJobScheduler.processInboundOrder();
//        batchJobScheduler.processCustomerMaster();

//        CompletableFuture.allOf(inboundOrder,outboundOrder,perpetualStockCountOrder,periodicStockCountOrder,stockAdjustmentOrder,inboundFailedOrder,outboundFailedOrder).join();

    }

}