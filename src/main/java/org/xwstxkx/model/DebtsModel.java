package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.xwstxkx.entity.DebtEntity;
import org.xwstxkx.util.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Долги")
public class DebtsModel {

    @Schema(description = "ID долга", example = "1")
    @NotBlank(message = "ID долга не может быть пустым")
    private Long id;
    @Schema(description = "Сумма долга (в копейках)", example = "10000")
    @NotBlank(message = "Сумма долга должна быть больше нуля")
    private Long amount;
    @Schema(description = "Имя должника")
    @NotBlank(message = "Имя должника не должно быть пустым")
    private String debtor;
    @Schema(description = "Дата, когда долг появился")
    @NotBlank(message = "Дата появления долга должна быть указана")
    private LocalDate date;
    @Schema(description = "Дата, когда долг был погашён")
    @NotBlank(message = "Дата погашения долга должна быть указана")
    private LocalDate dueDate;
    @Schema(description = "Статус долга", example = "ACTIVE")
    @NotBlank(message = "Статус долга должен быть выбран")
    private Status status;
    @Schema(description = "Коментарий долга (необязательный)", example = "Взял 50, вернёт 55")
    @Size(max = 1000, message = "Описание должно быть не более 1000 символов")
    private String description;

    public static DebtsModel toModel(DebtEntity entity) {
        return DebtsModel.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .debtor(entity.getDebtor())
                .date(entity.getDate())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .description(entity.getDescription())
                .build();
    }

    public static List<DebtsModel> toListModel(List<DebtEntity> entities) {
        List<DebtsModel> models = new ArrayList<>();
        for (DebtEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static DebtEntity toEntity(DebtsModel model) {
        return DebtEntity.builder()
                .id(model.getId())
                .amount(model.getAmount())
                .debtor(model.getDebtor())
                .date(model.getDate())
                .dueDate(model.getDueDate())
                .status(model.getStatus())
                .description(model.getDescription())
                .build();
    }

}


