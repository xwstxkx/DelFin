package org.duck.duckbackend.model;

import lombok.*;
import org.duck.duckbackend.entity.BudgetEntity;
import org.duck.duckbackend.enums.CategoryEnum;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetModel {

    private Long id;
    private CategoryEnum category;
    private String period;
    private Long sum;
    private Long user_id;

    public static BudgetModel toModel(BudgetEntity entity) {
        return BudgetModel.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .sum(entity.getSum())
                .period(entity.getPeriod())
                .user_id(entity.getUser().getId())
                .build();
    }

    public static List<BudgetModel> toListModel(List<BudgetEntity> entities) {
        List<BudgetModel> models = new ArrayList<>();
        for (BudgetEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static BudgetEntity toEntity(BudgetModel model) {
        return BudgetEntity.builder()
                .id(model.getId())
                .category(model.getCategory())
                .sum(model.getSum())
                .period(model.getPeriod())
                .build();
    }

    public static List<BudgetEntity> toListEntity(List<BudgetModel> models) {
        List<BudgetEntity> entities = new ArrayList<>();
        for (BudgetModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
