package com.courier.overc360.api.midmile.primary.model.piecedetails;

import lombok.Data;

import java.io.Serializable;

@Data
public class PieceDetailsCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `C_ID`, `PARTNER_ID`, `MAWB`, `HAWB`, `PIECE_ID`
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String pieceId;

}