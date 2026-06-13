package com.courier.overc360.api.common.model.consignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class PieceDetailsCompositeKey {

    @PrimaryKeyColumn(name = "pieceId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column(name = "pieceId")
    private Long pieceId;

    @PrimaryKeyColumn(name = "consignmentId", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Long consignmentId;

}