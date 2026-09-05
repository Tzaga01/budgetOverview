package org.imransbudget.budgetoverview.service;

import org.imransbudget.budgetoverview.dto.TransactionSummary;
import org.imransbudget.budgetoverview.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionServiceTest {

    private final TransactionService transactionService = new TransactionService();

    @Test
    void testProcessCsvFile_Categorization() {
        String csvContent = "Datum;Belopp;Avsändare;Mottagare;Rubrik;Saldo\n" +
                "2026-06-28;-100,00;;;Kortköp 260628 SWEDAVIA;500,00\n" +
                "2026-06-29;-200,00;;;Överföring **** ***  **;300,00\n" +
                "2026-06-30;500,00;;;Lön;800,00";

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        TransactionSummary summary = transactionService.processCsvFile(file);

        assertNotNull(summary);
        assertEquals(new BigDecimal("200.00"), summary.getTotalAmount());
        assertEquals(new BigDecimal("-100.00"), summary.getCategorizedTotals().get(TransactionType.CARD_PAYMENT));
        assertEquals(new BigDecimal("-200.00"), summary.getCategorizedTotals().get(TransactionType.TRANSFER));
        assertEquals(new BigDecimal("500.00"), summary.getCategorizedTotals().get(TransactionType.SALARY));
    }
}
