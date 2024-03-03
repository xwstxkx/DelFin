package org.duck.duckbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.duck.duckbackend.enums.CategoryEnum;

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
    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private CategoryEnum category;
    @Column(name = "period")
    private String period;
    @Column(name = "sum")
    private Long sum; //!!!В копейках
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
