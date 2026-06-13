package com.tekclover.wms.core.model.transaction;

import lombok.Data;

@Data
public class MobileDashboard {
	private InboundCount inboundCount;
	private OutboundCount outboundCount;
	private StockCount stockCount;
	
	@Data
	public class InboundCount {
		private Long cases;
		private Long putaway;
		private Long reversals;
	}
	
	@Data
	public class OutboundCount {
		private Long picking;
		private Long quality;
		private Long reversals;
<<<<<<< HEAD
		private Long quality2;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	}
	
	@Data
	public class StockCount {
		private Long perpertual;
		private Long periodic;
	}
}