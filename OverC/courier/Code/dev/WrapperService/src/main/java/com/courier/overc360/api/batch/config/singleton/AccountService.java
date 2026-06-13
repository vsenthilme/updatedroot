package com.courier.overc360.api.batch.config.singleton;

import java.util.ArrayList;
import java.util.List;

import com.courier.overc360.api.batch.scheduler.entity.InventoryMovement;

public class AccountService {

	private static List<InventoryMovement> inventoryHolder;
	private InventoryMovement inventoryMovement;
	
	public AccountService () {
		inventoryHolder = new ArrayList<>(50000);
	}
	
	public List<InventoryMovement> getInventoryHolder() {
		return inventoryHolder;
	}
	
	public void setInventoryHolder(List<InventoryMovement> items) {
		AccountService.inventoryHolder.addAll(items);
	}
	
	public InventoryMovement getInventory() {
		return inventoryMovement;
	}
	
	public void setInventory(InventoryMovement inventoryMovement) {
		this.inventoryMovement = inventoryMovement;
		AccountService.inventoryHolder.add(inventoryMovement);
	}
}