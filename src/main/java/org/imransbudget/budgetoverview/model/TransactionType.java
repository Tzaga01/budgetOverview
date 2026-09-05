package org.imransbudget.budgetoverview.model;

import lombok.Getter;

/**
 * Enum representing different categories of bank transactions, 
 * specifically tailored for Swedish banks like Nordea.
 */
@Getter
public enum TransactionType {
    CARD_PAYMENT("Kortköp"),
    AUTOGIRO("Autogiro"),
    TRANSFER("Överföring"),
    PAYMENT("Betalning"),
    SALARY("Lön"),
    INTEREST("Ränta"),
    WITHDRAWAL("Uttag"),
    SWISH("Swish"),
    OTHER("Övrigt");

    private final String swedishName;

    TransactionType(String swedishName) {
        this.swedishName = swedishName;
    }

    public static TransactionType fromSwedishName(String description) {
        if (description == null || description.isEmpty()) {
            return OTHER;
        }
        for (TransactionType type : values()) {
            if (description.startsWith(type.swedishName)) {
                return type;
            }
        }
        return OTHER;
    }
}
