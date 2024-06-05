package org.xwstxkx.model;

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

    private Long id;
    private String title;
    private boolean isRefreshed;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
    private Long sum;
    private Long user_id;
    private Long category_id;
    private List<ExpenseModel> expenses;
    private List<IncomeModel> incomes;

    public static BudgetModel toModel(BudgetEntity entity) {
        return BudgetModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isRefreshed(entity.isRefreshed())
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
