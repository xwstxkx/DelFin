package org.xwstxkx.model;

import lombok.*;
import org.xwstxkx.entity.SavingsBudgetEntity;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsBudgetModel {

    private Long id;
    private String title;
    private Long sum;
    private Long user_id;

    public static SavingsBudgetModel toModel(SavingsBudgetEntity entity) {
        return SavingsBudgetModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .sum(entity.getSum())
                .user_id(entity.getUser().getId())
                .build();
    }

    public static List<SavingsBudgetModel> toListModel(List<SavingsBudgetEntity> entities) {
        List<SavingsBudgetModel> models = new ArrayList<>();
        for (SavingsBudgetEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static SavingsBudgetEntity toEntity(SavingsBudgetModel model) {
        return SavingsBudgetEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .sum(model.getSum())
                .build();
    }

    public static List<SavingsBudgetEntity> toListEntity(List<SavingsBudgetModel> models) {
        List<SavingsBudgetEntity> entities = new ArrayList<>();
        for (SavingsBudgetModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
