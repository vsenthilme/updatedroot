package com.courier.overc360.api.idmaster.replica.model.route;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaRouteCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `ROUTE_ID`,'LEG_ID'
     */

    private String companyId;
    private String languageId;
    private String routeId;

}
