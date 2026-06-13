package com.courier.overc360.api.common.service;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class PDFSplitService {

    public void splitPdf(String sourceFilePath, String destinationDir) throws IOException {
        File srcFile = new File(sourceFilePath);
        if (!srcFile.exists()) {
            throw new IOException("Source file does not exist: " + sourceFilePath);
        }

        File destDir = new File(destinationDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        PDDocument document = PDDocument.load(srcFile);
        Splitter splitter = new Splitter();
        List<PDDocument> Pages = splitter.split(document);
        Iterator<PDDocument> iterator = Pages.listIterator();

        int i = 1;
        while (iterator.hasNext()) {
            PDDocument pd = iterator.next();
            pd.save(destinationDir + "splitted_pdf_" + i++ + ".pdf");
        }

        document.close();
    }
}
