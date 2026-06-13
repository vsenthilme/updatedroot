package com.courier.overc360.api.idmaster.replica.model.courierpartner;

import lombok.Data;

import java.util.List;

@Data
public class FindCourierPartner {

    List<String> companyId;
    List<String> languageId;
    List<String> courierPartnerId;
    List<String> statusId;
    List<String> partnerId;
}
