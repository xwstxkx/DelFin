package org.xwstxkx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @NotNull
    @Size(min = 3, max = 100)
    private String title;
    @Column(name = "period_end")
    private LocalDate periodEnd;
    @Column(name = "sum")
    private Long sum; //!!!В копейках
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget")
    private List<ExpenseEntity> expenses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget")
    private List<IncomeEntity> incomes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

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
}
