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
    private String category;
    private LocalDate periodEnd;
    private Long sum;
    private Long user_id;
    private List<TransactionModel> transactions;

    public static BudgetModel toModel(BudgetEntity entity) {
        return BudgetModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .category(entity.getCategory())
                .sum(entity.getSum())
                .periodEnd(entity.getPeriodEnd())
                .user_id(entity.getUser().getId())
                .transactions(TransactionModel.toListModel(entity.getTransactions()))
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
//        if (LocalDate.now().isAfter(model.getPeriodEnd())) {
//
//        } Завязка к накопительному бюджету по истечении времени,
//        в методе toModel сделать так же
        return BudgetEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .category(model.getCategory())
                .sum(model.getSum())
                .periodEnd(model.getPeriodEnd())
                .transactions(TransactionModel.toListEntity(model.getTransactions()))
                .build();
    }

    public static List<BudgetEntity> toListEntity(List<BudgetModel> models) throws ObjectNotFound {
        List<BudgetEntity> entities = new ArrayList<>();
        for (BudgetModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }

    public List<TransactionModel> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }
}
