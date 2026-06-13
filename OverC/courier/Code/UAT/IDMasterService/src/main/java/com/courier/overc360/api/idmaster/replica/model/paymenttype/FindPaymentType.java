package com.courier.overc360.api.idmaster.replica.model.paymenttype;

import lombok.Data;

import java.util.List;

@Data
public class FindPaymentType {

    List<String> languageId;
    List<String> companyId;
    List<String> paymentTypeId;
}
