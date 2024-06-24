package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.MainBudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.service.BudgetService;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.model.entity.BudgetModel;
import org.xwstxkx.model.entity.CategoryModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.repository.MainBudgetRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BudgetsCRUDService {

    private final BudgetRepository budgetRepository;
    private final CategoriesCRUDService categoriesCRUDService;
    private final BudgetService budgetService;
    private final MainBudgetRepository mainBudgetRepository;
    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public MainBudgetEntity getMainBudget(UserModel user) throws ObjectNotFound {
        return mainBudgetRepository.findByUser(getUser(user.getId()));
    }


    public BudgetModel getBudget(Long id, UserModel user) throws ObjectNotFound {
        BudgetEntity entity = budgetRepository.findByIdAndUser(id, getUser(user.getId()));
        if (budgetService.isBudgetValid(id)) {
            return BudgetModel.toModel(entity);
        } else if (!budgetService.isBudgetValid(id) && !entity.isRefreshed()) {
            return null;
        }
        return null;
    }

    @Transactional
    public void budgetSave(BudgetModel budgetModel, Long category_id, UserModel userModel)
            throws ObjectNotFound, ObjectWithThisNameIsAlreadyExists, BadCredentials {
        UserEntity user = getUser(userModel.getId());
        BudgetEntity budgetEntity = BudgetModel.toEntity(budgetModel);
        if (budgetEntity.getUser() != null && budgetEntity.getUser() != user) {
            throw new BadCredentials();
        } else {
            budgetEntity.setUser(user);
            budgetEntity.setCategory(CategoryModel.toEntity(
                    categoriesCRUDService.getCategory(category_id, userModel))
            );
            budgetEntity.setMainBudget(user.getMainBudget());
            int count = 0;
            List<BudgetEntity> budgetEntities = budgetRepository.findAllByUser(user);
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

    public List<BudgetModel> getAllBudgets(PageRequest pageRequest, UserModel userModel)
            throws ObjectNotFound {
        UserEntity user = getUser(userModel.getId());
        Page<BudgetEntity> page = budgetRepository.findAllByUser(user, pageRequest);
        log.info("Список бюджетов пользователя найден");
        return BudgetModel.toListModel(page.getContent());
    }

    @Transactional
    public void deleteBudget(Long id, UserModel user) throws ObjectNotFound {
        budgetRepository.deleteByIdAndUser(id, getUser(user.getId()));
        log.info("Бюджет успешно удалён");
    }
}
