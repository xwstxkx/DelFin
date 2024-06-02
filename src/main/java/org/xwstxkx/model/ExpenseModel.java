package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.xwstxkx.entity.ExpenseEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.util.CategoryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Расходы")
public class ExpenseModel {

    @Schema(description = "ID расхода", example = "1")
    @NotBlank(message = "ID расхода не может быть пустым")
    private Long id;
    @Schema(description = "Сумма расхода (в копейках)", example = "10000")
    @NotBlank(message = "Сумма расхода должна быть больше нуля")
    private Long amount;
    @Schema(description = "Категория")
    @NotBlank(message = "Категория должна быть выбрана")
    private Long category_id;
    @Schema(description = "Бюджет")
    @NotBlank(message = "Бюджет должен быть выбран")
    private Long budget_id;
    @Schema(description = "Дата расхода")
    @NotBlank(message = "Дата расхода не может быть пустой")
    private LocalDate date;
    @Schema(description = "Тип категорий расхода", example = "EXPENSE")
    @Size(min = 1, max = 50, message = "Тип категорий расходов должен содержать от 1 до 50 символов")
    @NotBlank(message = "Тип категории расхода должен быть указан")
    private CategoryType type;
    @Schema(description = "Коментарий расхода (необязательный)", example = "На билеты")
    @Size(max = 1000, message = "Описание должно быть не более 1000 символов")
    private String description;

    public static ExpenseModel toModel(ExpenseEntity entity) {
        return ExpenseModel.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .type(entity.getType())
                .description(entity.getDescription())
                .category_id(entity.getCategory().getId())
                .build();
    }

    public static List<ExpenseModel> toListModel(List<ExpenseEntity> entities) {
        List<ExpenseModel> models = new ArrayList<>();
        for (ExpenseEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static ExpenseEntity toEntity(ExpenseModel model) throws ObjectNotFound {
        return ExpenseEntity.builder()
                .id(model.getId())
                .amount(model.getAmount())
                .date(model.getDate())
                .type(model.getType())
                .description(model.getDescription())
                .build();
    }

    public static List<ExpenseEntity> toListEntity(List<ExpenseModel> models) throws ObjectNotFound {
        List<ExpenseEntity> entities = new ArrayList<>();
        for (ExpenseModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
