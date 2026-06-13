package com.tekclover.wms.api.transaction.model.inbound.gr.v2;

import com.tekclover.wms.api.transaction.model.inbound.gr.GrHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class GrHeaderV2 extends GrHeader {

	@Column(name = "THREE_PL_CBM")
	private Double threePlCbm;

	@Column(name = "THREE_PL_UOM", columnDefinition = "nvarchar(100)")
	private String threePlUom;

	@Column(name = "THREE_BILL_STATUS", columnDefinition = "nvarchar(100)")
	private String threeBillStatus;

	@Column(name = "THREE_LENGTH")
	private Double threeLength;

	@Column(name = "THREE_WIDTH")
	private Double threeWidth;

	@Column(name = "THREE_HEIGHT")
	private Double threeHeight;

	@Column(name = "C_TEXT", columnDefinition = "nvarchar(255)")
	private String companyDescription;

	@Column(name = "PLANT_TEXT", columnDefinition = "nvarchar(255)")
	private String plantDescription;

	@Column(name = "WH_TEXT", columnDefinition = "nvarchar(255)")
	private String warehouseDescription;
}
