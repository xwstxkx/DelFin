package org.xwstxkx.model;

import lombok.*;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.util.Role;

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
    private List<BudgetModel> budgets;

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
                .build();
    }

    public static List<UserModel> toListModel(List<UserEntity> entities) {
        List<UserModel> models = new ArrayList<>();
        for (UserEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static UserEntity toEntity(UserModel model) throws ObjectNotFound {
        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .password(model.getPassword())
                .email(model.getEmail())
                .budgets(BudgetModel.toListEntity(model.getBudgets()))
                .build();
    }

    public static List<UserEntity> toListEntity(List<UserModel> models) throws ObjectNotFound {
        List<UserEntity> entities = new ArrayList<>();
        for (UserModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
