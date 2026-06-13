package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SchedulerDto {
    @JsonProperty("scheduled_from")
    private Date scheduledFrom;

    @JsonProperty("scheduled_to")
    private Date scheduledTo;
}
