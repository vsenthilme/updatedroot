package com.tekclover.wms.api.mfg.model.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ASNV2 {
	
	@Valid
	private ASNHeaderV2 asnHeader;
	
	@Valid
	private List<ASNLineV2> asnLine;
}
