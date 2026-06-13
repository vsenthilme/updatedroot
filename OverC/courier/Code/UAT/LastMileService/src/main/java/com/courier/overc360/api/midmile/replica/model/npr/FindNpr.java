package com.courier.overc360.api.midmile.replica.model.npr;


import lombok.Data;

import java.util.List;

@Data
public class FindNpr {

    List<String> languageId;
    List<String> companyId;
    List<String> pickupId;


}

