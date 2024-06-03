package org.xwstxkx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.xwstxkx.util.CategoryType;
import org.xwstxkx.util.Status;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class LoanEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "ID займа не может быть пустым")
    private Long id;
    @Column(name = "amount")
    @NotBlank(message = "Сумма займа должна быть больше нуля")
    private Long amount;
    @Column(name = "lender")
    @NotBlank(message = "Имя кредитора не должно быть пустым")
    private String lender;
    @Column(name = "date")
    @NotBlank(message = "Дата вадачи займа должна быть указана")
    private LocalDate date;
    @Column(name = "due_date")
    @NotBlank(message = "Дата погашения займа должна быть указана")
    private LocalDate dueDate;
    @Column(name = "status")
    @NotBlank(message = "Статус займа должен быть выбран")
    private Status status;
    @Size(min = 1, max = 50, message = "Тип категорий займов должен содержать от 1 до 50 символов")
    @NotBlank(message = "Тип категории займа должен быть указан")
    private CategoryType type;
    @Column(name = "description")
    @Size(max = 1000, message = "Описание должно быть не более 1000 символов")
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
