package org.imransbudget.budgetoverview.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
public class TransactionService {

    public BigDecimal processCsvFile(MultipartFile file) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {

                if(line.trim().isEmpty()){
                    continue;
                }

                if(isHeader){
                    isHeader = false;
                    continue;
                }

                String[] columns = line.split(";");

                if(columns.length > 1){
                    String amountString = columns[1].trim();
                    BigDecimal amount = parseAmount(amountString);
                    totalAmount = totalAmount.add(amount);
                }
            }

            return totalAmount;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private BigDecimal parseAmount(String amountStr) {
        String cleaned = amountStr.replace("\"", "")
                .replaceAll("\\s+", "")
                .replace("\u00A0", "")
                .replace(",", ".");
        return new BigDecimal(cleaned);
    }
}
