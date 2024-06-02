package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.exceptions.ObjectNotFound;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Категории")
public class CategoryModel {

    @Schema(description = "ID категории", example = "1")
    @NotBlank(message = "ID не может быть пустым")
    private Long id;
    @Schema(description = "Название категории", example = "Путешествия")
    @NotBlank(message = "Название категории должно быть указано")
    private String name;
    @Schema(description = "Доходы")
    private List<IncomeModel> incomes;
    @Schema(description = "Расходы")
    private List<ExpenseModel> expenses;
    @Schema(description = "Бюджеты")
    private List<BudgetModel> budgets;

    public static CategoryModel toModel(CategoryEntity entity) {
        return CategoryModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .incomes(IncomeModel.toListModel(entity.getIncomes()))
                .expenses(ExpenseModel.toListModel(entity.getExpenses()))
                .budgets(BudgetModel.toListModel(entity.getBudgets()))
                .build();
    }

    public static List<CategoryModel> toListModel(List<CategoryEntity> entities) {
        List<CategoryModel> models = new ArrayList<>();
        for (CategoryEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static CategoryEntity toEntity(CategoryModel model) throws ObjectNotFound {
        return CategoryEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .incomes(IncomeModel.toListEntity(model.getIncomes()))
                .expenses(ExpenseModel.toListEntity(model.getExpenses()))
                .budgets(BudgetModel.toListEntity(model.getBudgets()))
                .build();
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

    public List<BudgetModel> getBudgets() {
        if (budgets == null) {
            budgets = new ArrayList<>();
        }
        return budgets;
    }
}
