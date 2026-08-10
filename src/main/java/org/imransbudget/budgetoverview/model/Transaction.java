package org.imransbudget.budgetoverview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String description;
    private Double amount;

}
