package org.xwstxkx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "savings_budgets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsBudgetEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @NotNull
    @Size(min = 3, max = 100)
    private String title;
    @Column(name = "sum")
    private Long sum; //!!!В копейках
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "main_budget_id")
    private MainBudgetEntity mainBudget;
}
