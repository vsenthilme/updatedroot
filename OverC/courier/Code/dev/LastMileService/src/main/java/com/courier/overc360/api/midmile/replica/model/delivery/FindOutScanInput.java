package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

import java.util.List;

@Data
public class FindOutScanInput {

    private List<String> manifestNumber;
}
