package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.xwstxkx.entity.LoanEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.util.CategoryType;
import org.xwstxkx.util.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Займы")
public class LoanModel {

    @Schema(description = "ID займа", example = "1")
    @NotBlank(message = "ID займа не может быть пустым")
    private Long id;
    @Schema(description = "Сумма займа (в копейках)", example = "10000")
    @NotBlank(message = "Сумма займа должна быть больше нуля")
    private Long amount;
    @Schema(description = "Имя кредитора")
    @NotBlank(message = "Имя кредитора не должно быть пустым")
    private String lender;
    @Schema(description = "Дата, когда займ получен")
    @NotBlank(message = "Дата получения займа должна быть указана")
    private LocalDate date;
    @Schema(description = "Дата, когда займ был погашён")
    @NotBlank(message = "Дата погашения займа должна быть указана")
    private LocalDate dueDate;
    @Schema(description = "Статус займа", example = "ACTIVE")
    @NotBlank(message = "Статус займа должен быть выбран")
    private Status status;
    @Schema(description = "Тип категорий займа", example = "LOAN")
    @Size(min = 1, max = 50, message = "Тип категорий займов должен содержать от 1 до 50 символов")
    @NotBlank(message = "Тип категории займа должен быть указан")
    private CategoryType type;
    @Schema(description = "Коментарий займа (необязательный)", example = "Взял 50, вернёт 55")
    @Size(max = 1000, message = "Описание должно быть не более 1000 символов")
    private String description;

    public static LoanModel toModel(LoanEntity entity) {
        return LoanModel.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .lender(entity.getLender())
                .date(entity.getDate())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .type(entity.getType())
                .description(entity.getDescription())
                .build();
    }

    public static List<LoanModel> toListModel(List<LoanEntity> entities) {
        List<LoanModel> models = new ArrayList<>();
        for (LoanEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static LoanEntity toEntity(LoanModel model) throws ObjectNotFound {
        return LoanEntity.builder()
                .id(model.getId())
                .amount(model.getAmount())
                .lender(model.getLender())
                .date(model.getDate())
                .dueDate(model.getDueDate())
                .status(model.getStatus())
                .type(model.getType())
                .description(model.getDescription())
                .build();
    }
}
