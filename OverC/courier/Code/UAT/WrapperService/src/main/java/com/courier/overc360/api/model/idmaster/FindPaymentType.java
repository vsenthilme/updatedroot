package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindPaymentType {

    List<String> languageId;
    List<String> companyId;
    List<String> paymentTypeId;
}
