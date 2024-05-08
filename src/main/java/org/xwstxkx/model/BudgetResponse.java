package org.xwstxkx.model;

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
