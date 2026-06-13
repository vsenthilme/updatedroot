package com.tekclover.wms.api.transaction.model.inbound.inventory.v2;

import com.tekclover.wms.api.transaction.model.inbound.inventory.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class InventoryV2 extends Inventory {

	@Column(name = "THREE_PL_ST_IND")
	private Long threePlStockIndicator;

	@Column(name = "THREE_PL_PARTNER_ID", columnDefinition = "nvarchar(100)")
	private String threePlPartnerId;

	@Column(name = "THREE_PL_GR_DATE")
	private Date threePlGrDate;

	@Column(name = "THREE_PL_CBM")
	private Double threePlCbm;

	@Column(name = "THREE_PL_UOM", columnDefinition = "nvarchar(100)")
	private String threePlUom;

	@Column(name = "THREE_PL_CBM_PER_QTY")
	private Double threePlCbmPerQty;

	@Column(name = "C_TEXT", columnDefinition = "nvarchar(255)")
	private String companyDescription;

	@Column(name = "PLANT_TEXT", columnDefinition = "nvarchar(255)")
	private String plantDescription;

	@Column(name = "WH_TEXT", columnDefinition = "nvarchar(255)")
	private String warehouseDescription;


}
