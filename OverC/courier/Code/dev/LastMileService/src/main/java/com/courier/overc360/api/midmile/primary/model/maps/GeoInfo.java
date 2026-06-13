package com.courier.overc360.api.midmile.primary.model.maps;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class GeoInfo {

    String address;

    Double latitude;

    Double longitude;
}
