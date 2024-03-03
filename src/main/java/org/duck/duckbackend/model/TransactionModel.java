package org.duck.duckbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.duck.duckbackend.entity.TransactionEntity;
import org.duck.duckbackend.enums.CategoryEnum;
import org.duck.duckbackend.enums.TypeEnum;

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
    private String name;
    private String UID;
    private String date;
    private TypeEnum type;
    private CategoryEnum category;
    private Long sum;
    private Long user_id;

    public static TransactionModel toModel(TransactionEntity entity) {
        return TransactionModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .UID(entity.getUID())
                .date(entity.getDate())
                .type(entity.getType())
                .sum(entity.getSum())
                .category(entity.getCategory())
                .user_id(entity.getUser().getId())
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
                .name(model.getName())
                .UID(model.getUID())
                .date(model.getDate())
                .type(model.getType())
                .sum(model.getSum())
                .category(model.getCategory())
                .build();
    }

    public static List<TransactionEntity> toListEntity(List<TransactionModel> models) {
        List<TransactionEntity> entities = new ArrayList<>();
        for (TransactionModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
