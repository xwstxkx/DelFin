package org.duck.duckbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.duck.duckbackend.enums.TypeEnum;

import java.sql.Time;
import java.util.Date;

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
    @Column(name = "name")
    private String name;
    @Column(name = "uid")
    private String UID;
    @Column(name = "date")
    private Date date;
    @Column(name = "time")
    private Time time;
    @Column(name = "type")
    private TypeEnum type;
    @Column(name = "sum")
    private Long sum; //!!!В копейках
    //Категория
    //Пользователь
}
