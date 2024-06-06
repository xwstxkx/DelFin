package org.xwstxkx.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.exceptions.ObjectNotFound;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetModel {

    @Schema(description = "ID бюджета", example = "1L")
    @NotBlank(message = "ID бюджета не может быть пустым")
    private Long id;
    @Schema(description = "Название бюджета", example = "Еда")
    @NotBlank(message = "Название бюджета не может быть пустым")
    private String title;
    @Schema(description = "Периодичность бюджета", example = "true")
    @NotBlank(message = "Периодичность бюджета не может быть пустой")
    private boolean isRefreshed;
    //    @Schema(description = "Валидность бюджета", example = "false")
//    @NotBlank(message = "Валидность бюджета не может быть пустой")
//    private boolean isValid;
    @Schema(description = "Дата начала бюджета", example = "2024-05-06")
    @NotBlank(message = "Дата начала не может быть пустой")
    private LocalDate periodBegin;
    @Schema(description = "Дата конца бюджета", example = "2024-06-06")
    @NotBlank(message = "Дата конца не может быть пустой")
    private LocalDate periodEnd;
    @Schema(description = "Сумма бюджета", example = "10000")
    @NotBlank(message = "Сумма бюджета не может быть пустой")
    private Long sum;
    @Schema(description = "ID пользователя", example = "2L")
    @NotBlank(message = "ID пользователя не может быть пустым")
    private Long user_id;
    @Schema(description = "ID категории", example = "2L")
    @NotBlank(message = "ID категории не может быть пустым")
    private Long category_id;
    @Schema(description = "Список расходов")
    private List<ExpenseModel> expenses;
    @Schema(description = "Список доходов")
    private List<IncomeModel> incomes;

    public static BudgetModel toModel(BudgetEntity entity) {
        return BudgetModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isRefreshed(entity.isRefreshed())
//                .isValid(entity.isValid())
                .category_id(entity.getCategory().getId())
                .sum(entity.getSum())
                .periodBegin(entity.getPeriodBegin())
                .periodEnd(entity.getPeriodEnd())
                .user_id(entity.getUser().getId())
                .expenses(ExpenseModel.toListModel(entity.getExpenses()))
                .incomes(IncomeModel.toListModel(entity.getIncomes()))
                .build();
    }

    public static List<BudgetModel> toListModel(List<BudgetEntity> entities) {
        List<BudgetModel> models = new ArrayList<>();
        for (BudgetEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static BudgetEntity toEntity(BudgetModel model) throws ObjectNotFound {
        return BudgetEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .isRefreshed(model.isRefreshed())
//                .isValid(model.isValid)
                .sum(model.getSum())
                .periodBegin(model.getPeriodBegin())
                .periodEnd(model.getPeriodEnd())
                .expenses(ExpenseModel.toListEntity(model.getExpenses()))
                .incomes(IncomeModel.toListEntity(model.getIncomes()))
                .build();
    }

    public static List<BudgetEntity> toListEntity(List<BudgetModel> models) throws ObjectNotFound {
        List<BudgetEntity> entities = new ArrayList<>();
        for (BudgetModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }

    public List<IncomeModel> getIncomes() {
        if (incomes == null) {
            incomes = new ArrayList<>();
        }
        return incomes;
    }

    public List<ExpenseModel> getExpenses() {
        if (expenses == null) {
            expenses = new ArrayList<>();
        }
        return expenses;
    }
}
