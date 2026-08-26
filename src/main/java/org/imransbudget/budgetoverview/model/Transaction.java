package org.imransbudget.budgetoverview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private Double amount;

}
