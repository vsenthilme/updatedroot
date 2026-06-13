package com.courier.overc360.api.midmile.replica.model.console;

import java.util.Date;

public interface ConsoleImpl {

    String getTotalQuantity();

    String getTotalWeight();

    String getLangDesc();

    String getCompanyDesc();

    String getConsigneeName();

    String getOrigin();

    String getMasterAirwayBill();

    String getPartnerName();

    String getPartnerType();

    String getScannedBy();

    Date getScannedOn();

    String getConsoleId();

    String getPartnerId();

    String getAirportCode();

    String getReferenceField1();
    String getOriginFlightCountry();

}
