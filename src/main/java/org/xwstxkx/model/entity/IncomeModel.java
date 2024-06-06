package org.xwstxkx.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.xwstxkx.entity.IncomeEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.util.CategoryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "Доходы")
public class IncomeModel {

    @Schema(description = "ID дохода", example = "1")
    @NotBlank(message = "ID дохода не может быть пустым")
    private Long id;
    @Schema(description = "Сумма дохода (в копейках)", example = "10000")
    @NotBlank(message = "Сумма дохода должна быть больше нуля")
    private Long amount;
    @Schema(description = "Дата дохода")
    @NotBlank(message = "Дата дохода не может быть пустой")
    private LocalDate date;
    @Schema(description = "Категория")
    @NotBlank(message = "Категория должна быть выбрана")
    private Long category_id;
    @Schema(description = "Тип категории дохода", example = "Работа")
    @Size(min = 1, max = 50, message = "Тип категории доходов должен содержать от 1 до 50 символов")
    @NotBlank(message = "Тип категории дохода должен быть указан")
    private CategoryType type;
    @Schema(description = "Коментарий дохода (необязательный)", example = "На билеты")
    @Size(max = 1000, message = "Описание должно быть не более 1000 символов")
    private String description;

    public static IncomeModel toModel(IncomeEntity entity) {
        return IncomeModel.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .type(entity.getType())
                .description(entity.getDescription())
                .category_id(entity.getCategory().getId())
                .build();
    }

    public static List<IncomeModel> toListModel(List<IncomeEntity> entities) {
        List<IncomeModel> models = new ArrayList<>();
        for (IncomeEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static IncomeEntity toEntity(IncomeModel model) throws ObjectNotFound {
        return IncomeEntity.builder()
                .id(model.getId())
                .amount(model.getAmount())
                .date(model.getDate())
                .type(model.getType())
                .description(model.getDescription())
                .build();
    }

    public static List<IncomeEntity> toListEntity(List<IncomeModel> models) throws ObjectNotFound {
        List<IncomeEntity> entities = new ArrayList<>();
        for (IncomeModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }

}
