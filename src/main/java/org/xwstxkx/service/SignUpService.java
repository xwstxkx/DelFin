package org.xwstxkx.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CategoryModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.service.crud.CategoriesCRUDService;
import org.xwstxkx.service.security.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SignUpService {

    private UserService userService;
    private CategoriesCRUDService categoriesCRUDService;
    private BudgetRepository budgetRepository;

    public UserEntity getUser() {
        return userService.getCurrentUser();
    }

    @Transactional
    public void createSignUpEntities(UserEntity user) throws ObjectNotFound {
        categoryCreate();
        budgetCreate();
        log.info("Создан основной бюджет для пользователя '{}'", user.getUsername());
    }

    private void budgetCreate() throws ObjectNotFound {
        BudgetEntity mainBudget = BudgetEntity.builder()
                .title("Общий бюджет")
                .periodEnd(LocalDate.now().plusYears(10))
                .category(CategoryModel.toEntity(categoriesCRUDService
                        .getCategoryByName("Общая категория")))
                .sum(100000L)
                .user(getUser())
                .build();
        budgetRepository.save(mainBudget);
    }

    private void categoryCreate() {
        List<String> categoryNames = Arrays.asList(
                "Общая категория", "Еда", "Покупки",
                "Дом", "Образование", "Здоровье", "Транспорт",
                "Кафе", "Путешествия", "Питомцы"
        );

        categoryNames.forEach(categoryName -> {
            CategoryEntity categoryEntity = CategoryEntity.builder()
                    .user(getUser())
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
