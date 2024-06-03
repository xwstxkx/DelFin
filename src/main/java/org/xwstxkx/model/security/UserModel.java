package org.xwstxkx.model.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.util.Role;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Schema(description = "ID пользователя", example = "1")
    @NotBlank(message = "ID пользователя не может быть пустым")
    private Long id;
    @Schema(description = "Имя пользователя", example = "John")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;
    @Schema(description = "Адрес электронной почты", example = "johndoe@gmail.com")
    @Size(min = 8, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;
    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
    @Schema(description = "Роль пользователя")
    private Role role;
    @Schema(description = "Бюджеты пользователя")
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
}
