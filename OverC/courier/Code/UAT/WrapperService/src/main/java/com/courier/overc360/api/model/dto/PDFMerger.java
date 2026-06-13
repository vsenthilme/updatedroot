package com.courier.overc360.api.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PDFMerger {

    private List<String> filePaths;
    private String outputPath;

}
