package com.app.invoice.App.Controllers;

import com.app.invoice.App.Records.InvoiceItem;
import com.app.invoice.App.Services.PdfService;
import com.app.invoice.App.Utilities.ThymeleafUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;

@Controller
public class InvoiceController {

    @Autowired
    private ThymeleafUtil thymeleafUtil;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/supported-invoices")
    public String index(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "supported-invoices"; // This is the name of the Thymeleaf template (without the extension).
    }

    @GetMapping("/default")
    public String greet(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "pdf/default-invoice-template"; // This is the name of the Thymeleaf template (without the extension).
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<ByteArrayResource> generatePdf(Model model) throws Exception {
        // Populate model with data (if needed)
        model.addAttribute("title", "Invoice");
        model.addAttribute("customerName", "John Doe");

        // Sample invoice items (replace with your actual data)
        List<InvoiceItem> items = new ArrayList<>();
        items.add(new InvoiceItem("Item 1", "Description 1", 2, 50.0));
        items.add(new InvoiceItem("Item 2", "Description 2", 1, 75.0));

        model.addAttribute("items", items);

        // Calculate total amount
        double totalAmount = items.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
        model.addAttribute("totalAmount", totalAmount);
        // Add other attributes as needed

        // Render Thymeleaf template to HTML
        String html = thymeleafUtil.getHtmlFromTemplate("pdf/default-invoice-template", model);

        // Generate PDF using Flying Saucer and write to a byte array
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();

            byte[] rotatedPdf = pdfService.rotatePdf(outputStream.toByteArray());

            // Create a ByteArrayResource to represent the rotated PDF
            ByteArrayResource resource = new ByteArrayResource(rotatedPdf);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rotated.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(rotatedPdf.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        }
    }
}
