package com.ensa.microservicetransfer.web;

import com.ensa.microservicetransfer.services.TransferService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/pdftransfer")
@RestController
public class PdfExportController {
    @Autowired
    private TransferService transferService;
    @GetMapping("/pdf/{idTransfer}")
    public void generatePDF(HttpServletResponse response,@PathVariable String idTransfer) throws IOException{
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_"+currentDateTime+".pdf";
        response.setHeader(headerKey,headerValue);
        transferService.export(response,idTransfer);
    }
    @GetMapping("/pdftransferMultiple/{idTransferMultiple}")
    public void generatePDFForTransferMultiple(HttpServletResponse response,@PathVariable String idTransferMultiple) throws IOException{
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_"+currentDateTime+".pdf";
        response.setHeader(headerKey,headerValue);

        transferService.exportForTransferMultiple(response,idTransferMultiple);
    }
}
