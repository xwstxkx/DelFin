package org.xwstxkx.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.MainBudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CategoryModel;
import org.xwstxkx.repository.MainBudgetRepository;
import org.xwstxkx.service.crud.CategoriesCRUDService;
import org.xwstxkx.service.security.UserService;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SignUpService {

    private final UserService userService;
    private final CategoriesCRUDService categoriesCRUDService;
    private final MainBudgetRepository mainBudgetRepository;

    public UserEntity getUser() {
        return userService.getCurrentUser();
    }

    @Transactional
    public void createSignUpEntities(UserEntity user) {
        categoryCreate(user);
        budgetCreate(user);
        log.info("Создан основной бюджет для пользователя '{}'", user.getUsername());
    }

    private void budgetCreate(UserEntity user) {
        MainBudgetEntity mainBudget = MainBudgetEntity.builder()
                .title("Общий бюджет")
                .user(user)
                .build();
        mainBudgetRepository.save(mainBudget);
    }

    private void categoryCreate(UserEntity user) {
        List<String> categoryNames = Arrays.asList(
                "Общая категория", "Еда", "Покупки",
                "Дом", "Образование", "Здоровье", "Транспорт",
                "Кафе", "Путешествия", "Питомцы"
        );

        categoryNames.forEach(categoryName -> {
            CategoryEntity categoryEntity = CategoryEntity.builder()
                    .user(user)
                    .name(categoryName)
                    .build();
            try {
                categoriesCRUDService.saveCategory(CategoryModel.toModel(categoryEntity));
            } catch (ObjectNotFound e) {
                throw new RuntimeException(e);
            }
            log.info(categoryName + " успешно сохранена");
        });
    }
}
