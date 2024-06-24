package org.xwstxkx.entity;

import jakarta.persistence.*;
import lombok.*;
import org.xwstxkx.util.CategoryType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expenses")
public class ExpenseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "type")
    private CategoryType type;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "budget_id")
    private BudgetEntity budget;
    @ManyToOne
    @JoinColumn(name = "main_budget_id")
    private MainBudgetEntity mainBudget;
}
