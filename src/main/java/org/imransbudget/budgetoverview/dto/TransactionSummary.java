package org.imransbudget.budgetoverview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.imransbudget.budgetoverview.model.TransactionType;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummary {
    private BigDecimal totalAmount;
    private Map<TransactionType, BigDecimal> categorizedTotals;
}
