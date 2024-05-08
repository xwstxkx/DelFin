package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.xwstxkx.entity.TransactionEntity;
import org.xwstxkx.exceptions.ObjectNotFound;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель транзакции")
public class TransactionModel {

    private Long id;
    private String uid;
    private String date;
    private String type;
    private String category;
    private Long sum;
    private Long budget_id;

    public static TransactionModel toModel(TransactionEntity entity) {
        return TransactionModel.builder()
                .id(entity.getId())
                .uid(entity.getUid())
                .date(entity.getDate())
                .type(entity.getType())
                .sum(entity.getSum())
                .category(entity.getCategory())
                .budget_id(entity.getBudget().getId())
                .build();
    }

    public static List<TransactionModel> toListModel(List<TransactionEntity> entities) {
        List<TransactionModel> models = new ArrayList<>();
        for (TransactionEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static TransactionEntity toEntity(TransactionModel model) {
        return TransactionEntity.builder()
                .id(model.getId())
                .uid(model.getUid())
                .date(model.getDate())
                .type(model.getType())
                .sum(model.getSum())
                .category(model.getCategory())
                .build();
    }

    public static List<TransactionEntity> toListEntity(List<TransactionModel> models) throws ObjectNotFound {
        List<TransactionEntity> entities = new ArrayList<>();
        for (TransactionModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
