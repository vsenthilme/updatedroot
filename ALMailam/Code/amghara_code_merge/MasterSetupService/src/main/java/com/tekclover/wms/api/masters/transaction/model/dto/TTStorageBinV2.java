package com.tekclover.wms.api.masters.transaction.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(callSuper = true)
public class TTStorageBinV2 extends TStorageBin {

	@Column(name = "CAP_CHECK")
	private boolean capacityCheck;

	@Column(name="ALLOC_VOL")
	private Double allocatedVolume;

}