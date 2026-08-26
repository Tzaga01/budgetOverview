package org.imransbudget.budgetoverview.controller;

import org.imransbudget.budgetoverview.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try{
            BigDecimal total = transactionService.processCsvFile(file);
            return ResponseEntity.ok("Total amount from transactions: " + total + "kr");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Invalid file format" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error uploading file" + e.getMessage());
        }
    }
}
