package org.xwstxkx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.xwstxkx.util.Status;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "debts")
public class DebtEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "ID долга не может быть пустым")
    private Long id;
    @Column(name = "amount")
    @NotBlank(message = "Сумма долга должна быть больше нуля")
    private Long amount;
    @Column(name = "debtor")
    @NotBlank(message = "Имя должника не должно быть пустым")
    private String debtor;
    @Column(name = "date")
    @NotBlank(message = "Дата появления долга должна быть указана")
    private LocalDate date;
    @Column(name = "dueDate")
    @NotBlank(message = "Дата погашения долга должна быть указана")
    private LocalDate dueDate;
    @Column(name = "status")
    @NotBlank(message = "Статус долга должен быть выбран")
    private Status status;
    @Column(name = "description")
    @Size(min = 0, max = 1000, message = "Описание должно быть не более 1000 символов")
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}



