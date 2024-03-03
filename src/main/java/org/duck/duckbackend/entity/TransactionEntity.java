package org.duck.duckbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uid")
    private String UID;
    @Column(name = "date")
    private String date;
    @Column(name = "type")
    private String type;
    @Column(name = "category")
    private String category;
    @Column(name = "sum")
    private Long sum; //!!!В копейках
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "budget_id")
    private BudgetEntity budget;
}
