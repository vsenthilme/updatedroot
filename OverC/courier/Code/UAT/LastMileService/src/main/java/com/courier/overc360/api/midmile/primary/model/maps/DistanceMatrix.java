package com.courier.overc360.api.midmile.primary.model.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tbldistancematrix")
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

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    private Double distance;
    private String duration;

    @Column(name = "PICKUP_ID")
    private String pickupId;

    @Column(name = "DELIVERY_ID", columnDefinition = "nvarchar(50)")
    private String deliveryId;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;
}
