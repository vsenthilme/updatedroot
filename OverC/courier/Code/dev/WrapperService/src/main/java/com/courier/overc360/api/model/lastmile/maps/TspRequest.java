package com.courier.overc360.api.model.lastmile.maps;

import lombok.Data;

import java.util.List;

@Data
public class TspRequest {

    private List<String> addresses;
}
