package com.tekclover.wms.api.masters.transaction.model.warehouse.inbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ASNHeader {

	@Column(nullable = false)
	@NotBlank(message = "Warehouse ID is mandatory")
	private String wareHouseId;

	@Column(nullable = false)
	@NotBlank(message = "ASN Number is mandatory")
	private String asnNumber;
	
	@JsonIgnore
	private String id;
	
	@JsonIgnore
	private Date orderReceivedOn;
	
	@JsonIgnore
	private Long statusId;
}