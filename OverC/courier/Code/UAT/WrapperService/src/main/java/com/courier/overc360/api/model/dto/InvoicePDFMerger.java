package com.courier.overc360.api.model.dto;

import lombok.Data;

@Data
public class InvoicePDFMerger {

    private String path;

    private String outputPath;

    private String lineNo;
}
