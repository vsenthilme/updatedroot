package com.courier.overc360.api.midmile.primary.model.maps;

import lombok.Data;

import java.util.List;

@Data
public class TspRequest {

    private List<String> addresses;
}
