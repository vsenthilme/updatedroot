package com.mnrclara.api.accounting.model;

import lombok.Data;
import java.util.List;

@Data
public class Notification {
    private String title;
    private String message;
    private List<String> token;
    private Long classId;
    private String clientId;
    private String orderType;
    private String quotationHeaderNo;
}
