package com.courier.overc360.api.idmaster.replica.model.status;

import lombok.Data;

import java.util.List;

@Data
public class FindReplicaStatus {

    private List<String> languageId;
    private List<String> statusId;

}
