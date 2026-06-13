package com.tekclover.wms.api.transaction.model.inbound.putaway.v2;

import com.tekclover.wms.api.transaction.model.inbound.putaway.PutAwayLine;
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
public class PutAwayLineV2 extends PutAwayLine {

	@Column(name = "INV_QTY")
	private Double inventoryQuantity;

	@Column(name = "BARCODE_ID", columnDefinition = "nvarchar(150)")
	private String barcodeId;

	@Column(name = "MFR_DATE")
	private Date manufacturerDate;

	@Column(name = "EXP_DATE")
	private Date expiryDate;

	@Column(name = "MFR_CODE", columnDefinition = "nvarchar(100)")
	private String manufacturerCode;

	@Column(name = "MFR_NAME", columnDefinition = "nvarchar(150)")
	private String manufacturerName;

	@Column(name = "ORIGIN", columnDefinition = "nvarchar(100)")
	private String origin;

	@Column(name = "BRAND", columnDefinition = "nvarchar(100)")
	private String brand;

	@Column(name = "ORD_QTY")
	private Double orderQty;

	@Column(name = "CBM")
	private Double cbm;

	@Column(name = "CBM_UNIT", columnDefinition = "nvarchar(150)")
	private String cbmUnit;

	@Column(name = "CBM_PER_QTY")
	private Double cbmPerQuantity;

	@Column(name = "C_TEXT", columnDefinition = "nvarchar(255)")
	private String companyDescription;

	@Column(name = "PLANT_TEXT", columnDefinition = "nvarchar(255)")
	private String plantDescription;

	@Column(name = "WH_TEXT", columnDefinition = "nvarchar(255)")
	private String warehouseDescription;
}
