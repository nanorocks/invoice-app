package com.app.invoice.App.Services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] rotatePdf(byte[] originalPdf) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfReader reader = new PdfReader(originalPdf);
            int pageCount = reader.getNumberOfPages();

            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();

            for (int i = pageCount; i > 0; i--) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, i);
                copy.addPage(page);
            }

            document.close();
            reader.close();

            return outputStream.toByteArray();
        }
    }
}
