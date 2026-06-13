package com.tekclover.wms.api.transaction.model.inbound.preinbound.v2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tekclover.wms.api.transaction.model.inbound.preinbound.PreInboundLineEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class PreInboundLineEntityV2 extends PreInboundLineEntity {

	@Column(name = "MFR_CODE", columnDefinition = "nvarchar(255)")
	private String manufacturerCode;

	@Column(name = "MFR_NAME", columnDefinition = "nvarchar(255)")
	private String manufacturerName;

	@Column(name = "ORIGIN", columnDefinition = "nvarchar(255)")
	private String origin;
}
