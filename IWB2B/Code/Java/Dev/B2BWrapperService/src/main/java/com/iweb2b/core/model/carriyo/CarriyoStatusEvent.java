package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;

@Getter
@Setter
@NoRepositoryBean
@ToString
public class CarriyoStatusEvent {
    @JsonProperty("carriyo_status_code")
    private String carriyoStatusCode;

    @JsonProperty("carriyo_reason_code")
    private String carriyoReasonCode;

    @JsonProperty("carrier_status_code")
    private String carrierStatusCode;

    @JsonProperty("carrier_status_description")
    private String carrierStatusDescription;

    @JsonProperty("status_update_date")
    private Date statusUpdateDate;
}
