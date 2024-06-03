package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.model.CategoryModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BudgetsCRUDService {

    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final CategoriesCRUDService categoriesCRUDService;

    UserEntity getUser() {
        return userService.getCurrentUser();
    }


    public BudgetModel getBudget(Long id) {
        return BudgetModel.toModel(budgetRepository
                .findByIdAndUser(id, getUser()));
    }

    @Transactional
    public void budgetSave(BudgetModel budgetModel, Long category_id)
            throws ObjectNotFound, ObjectWithThisNameIsAlreadyExists, BadCredentials {
        BudgetEntity budgetEntity = BudgetModel.toEntity(budgetModel);
        if (budgetEntity.getUser() != null && budgetEntity.getUser() != getUser()) {
            throw new BadCredentials();
        } else {
            budgetEntity.setUser(userService.getCurrentUser());
            budgetEntity.setCategory(CategoryModel.toEntity(
                    categoriesCRUDService.getCategory(category_id))
            );
            int count = 0;
            List<BudgetEntity> budgetEntities = budgetRepository.findAllByUser(userService.getCurrentUser());
            for (BudgetEntity entity : budgetEntities) {
                if (budgetEntity.getTitle().equals(entity.getTitle())) {
                    count++;
                    if (count > 1) {
                        throw new ObjectWithThisNameIsAlreadyExists();
                    }
                }
            }
            budgetRepository.save(budgetEntity);
            log.info("Бюджет сохранён");
        }
    }

    public List<BudgetModel> getAllBudgets(PageRequest pageRequest) {
        UserEntity userEntity = userService.getCurrentUser();
        Page<BudgetEntity> page = budgetRepository.findAllByUser(userEntity, pageRequest);
        log.info("Список бюджетов пользователя найден");
        return BudgetModel.toListModel(page.getContent());
    }

    @Transactional
    public void deleteBudget(Long id) {
        budgetRepository.deleteByIdAndUser(id, getUser());
        log.info("Бюджет успешно удалён");
    }
}
