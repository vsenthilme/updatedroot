package com.courier.overc360.api.model.lastmile.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class DistanceMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_location_id")
    private Location fromLocationId;

    @ManyToOne
    @JoinColumn(name = "to_location_id")
    private Location toLocationId;

    @ManyToOne
    @JoinColumn(name = "from_miss_location_id")
    private MissingLocation fromMissLocationId;

    @ManyToOne
    @JoinColumn(name = "to_miss_location_id")
    private MissingLocation toMissLocationId;

    private String fromAddress;

    private String toAddress;

    private Double distance;
    private String duration;

    private String pickupId;

    private String deliveryId;

    private String houseAirwayBill;
}
