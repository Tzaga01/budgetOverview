package org.imransbudget.budgetoverview.service;

import org.imransbudget.budgetoverview.dto.TransactionSummary;
import org.imransbudget.budgetoverview.model.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

@Service
public class TransactionService {

    public TransactionSummary processCsvFile(MultipartFile file) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        Map<TransactionType, BigDecimal> categorizedTotals = new EnumMap<>(TransactionType.class);

        // Initialize map with zeros
        for (TransactionType type : TransactionType.values()) {
            categorizedTotals.put(type, BigDecimal.ZERO);
        }

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

                    // Categorization
                    if (columns.length > 5) {
                        String description = columns[5].trim();
                        TransactionType type = TransactionType.fromSwedishName(description);
                        categorizedTotals.put(type, categorizedTotals.get(type).add(amount));
                    } else {
                        categorizedTotals.put(TransactionType.OTHER, categorizedTotals.get(TransactionType.OTHER).add(amount));
                    }
                }
            }

            return TransactionSummary.builder()
                    .totalAmount(totalAmount)
                    .categorizedTotals(categorizedTotals)
                    .build();

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
