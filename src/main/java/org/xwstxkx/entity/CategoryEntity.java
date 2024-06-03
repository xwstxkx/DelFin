package org.xwstxkx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation_categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<IncomeEntity> incomes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<ExpenseEntity> expenses;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<BudgetEntity> budgets;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity user;

    public List<IncomeEntity> getIncomes() {
        if (incomes == null) {
            incomes = new ArrayList<>();
        }
        return incomes;
    }

    public List<ExpenseEntity> getExpenses() {
        if (expenses == null) {
            expenses = new ArrayList<>();
        }
        return expenses;
    }

    public List<BudgetEntity> getBudgets() {
        if (budgets == null) {
            budgets = new ArrayList<>();
        }
        return budgets;
    }
}
