package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import java.util.Date;

@Data
public class StatusCountInput {

    private Date fromDate;
    private Date toDate;
}
