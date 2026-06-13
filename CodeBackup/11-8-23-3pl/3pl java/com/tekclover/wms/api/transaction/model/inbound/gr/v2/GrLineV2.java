package com.tekclover.wms.api.transaction.model.inbound.gr.v2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tekclover.wms.api.transaction.model.inbound.gr.GrLine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class GrLineV2 extends GrLine {

	@Column(name = "THREE_PL_CBM")
	private Double threePlCbm;

	@Column(name = "THREE_PL_UOM", columnDefinition = "nvarchar(100)")
	private String threePlUom;

	@Column(name = "THREE_PL_BILL_STATUS", columnDefinition = "nvarchar(100)")
	private String threePlBillStatus;

	@Column(name = "THREE_PL_LENGTH")
	private Double threePlLength;

	@Column(name = "THREE_PL_WIDTH")
	private Double threePlWidth;

	@Column(name = "THREE_PL_HEIGHT")
	private Double threePlHeight;

	@Column(name = "CBM_PER_QTY")											//CBM Per Qty
	private Double cbmPerQuantity;

	@Column(name = "C_TEXT", columnDefinition = "nvarchar(255)")
	private String companyDescription;

	@Column(name = "PLANT_TEXT", columnDefinition = "nvarchar(255)")
	private String plantDescription;

	@Column(name = "WH_TEXT", columnDefinition = "nvarchar(255)")
	private String warehouseDescription;

	@Column(name = "ST_BIN_INTM", columnDefinition = "nvarchar(100)")
	private String interimStorageBin;
}
