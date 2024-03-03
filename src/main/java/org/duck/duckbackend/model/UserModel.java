package org.duck.duckbackend.model;

import lombok.*;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.util.Role;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private List<TransactionModel> transactions;
    private List<BudgetModel> budgets;

    public List<TransactionModel> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    public List<BudgetModel> getBudgets() {
        if (budgets == null) {
            budgets = new ArrayList<>();
        }
        return budgets;
    }

    public static UserModel toModel(UserEntity entity) {
        return UserModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .budgets(BudgetModel.toListModel(entity.getBudgets()))
                .transactions(TransactionModel.toListModel(entity.getTransactions()))
                .build();
    }

    public static List<UserModel> toListModel(List<UserEntity> entities) {
        List<UserModel> models = new ArrayList<>();
        for (UserEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static UserEntity toEntity(UserModel model) {
        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .password(model.getPassword())
                .email(model.getEmail())
                .budgets(BudgetModel.toListEntity(model.getBudgets()))
                .transactions(TransactionModel.toListEntity(model.getTransactions()))
                .build();
    }

    public static List<UserEntity> toListEntity(List<UserModel> models) {
        List<UserEntity> entities = new ArrayList<>();
        for (UserModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
