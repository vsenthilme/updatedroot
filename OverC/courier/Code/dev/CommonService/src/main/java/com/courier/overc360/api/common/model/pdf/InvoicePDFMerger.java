package com.courier.overc360.api.common.model.pdf;

import lombok.Data;

@Data
public class InvoicePDFMerger {

    private String path;

    private String outputPath;

    private String lineNo;
}
