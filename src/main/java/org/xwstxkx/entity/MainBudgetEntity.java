package org.xwstxkx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "main_budgets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainBudgetEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @NotNull
    @Size(min = 3, max = 100)
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainBudget")
    private List<ExpenseEntity> expenses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainBudget")
    private List<IncomeEntity> incomes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainBudget")
    private List<BudgetEntity> budgets = new ArrayList<>();

}