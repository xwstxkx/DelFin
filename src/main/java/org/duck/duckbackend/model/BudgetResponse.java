package org.duck.duckbackend.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {

    private Long amount;
    private Long spentAmount;
    private Long balance;
}
