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
    private Long id;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "lender")
    private String lender;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "due_date")
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
