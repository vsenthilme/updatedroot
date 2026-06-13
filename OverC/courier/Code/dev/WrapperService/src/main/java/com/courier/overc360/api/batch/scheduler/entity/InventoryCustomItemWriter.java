package com.courier.overc360.api.batch.scheduler.entity;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.annotation.PreDestroy;

import com.courier.overc360.api.batch.config.singleton.AccountService;
import com.courier.overc360.api.batch.config.singleton.AppConfig;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InventoryCustomItemWriter implements ItemWriter<InventoryMovement>, Closeable {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		AccountService service1 = null;
		
	    public InventoryCustomItemWriter() {
	    	service1 = context.getBean("accountService", AccountService.class);
	    }

	    @Override
	    public void write(final List<? extends InventoryMovement> items) throws Exception {
	    	service1.getInventoryHolder().addAll(items);
	    }

	    @PreDestroy
	    @Override
	    public void close() throws IOException {
	        this.close();
	    }
	}