package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.IncomeEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.model.entity.IncomeModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.repository.CategoryRepository;
import org.xwstxkx.repository.IncomeRepository;
import org.xwstxkx.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class IncomesCRUDService {


    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public String saveIncome(IncomeModel model, Long category_id,
                             Long budget_id, UserModel userModel)
            throws ObjectNotFound, BadCredentials {
        IncomeEntity entity = IncomeModel.toEntity(model);
        if (entity.getUser() != null && entity.getUser() != getUser(userModel.getId())) {
            throw new BadCredentials();
        } else {
            entity.setUser(getUser(userModel.getId()));
            entity.setCategory(categoryRepository
                    .findById(category_id)
                    .orElseThrow(ObjectNotFound::new));
            entity.setBudget(budgetRepository
                    .findById(budget_id)
                    .orElseThrow(ObjectNotFound::new));
            incomeRepository.save(entity);
            log.info("Доход сохранён успешно");
            return "Доход сохранён успешно";
        }
    }

    public IncomeModel getIncome(Long id, UserModel userModel) throws ObjectNotFound {
        IncomeEntity income = incomeRepository
                .findByIdAndUser(id, getUser(userModel.getId()));
        log.info("Доход был найден");
        return IncomeModel.toModel(income);
    }

    public List<IncomeModel> getAllIncomes(UserModel userModel) throws ObjectNotFound {
        List<IncomeEntity> incomes = incomeRepository
                .findAllByUser(getUser(userModel.getId()));
        log.info("Все доходы были успешно найдены");
        return IncomeModel.toListModel(incomes);
    }

    @Transactional
    public String deleteIncomes(Long id, UserModel userModel) throws ObjectNotFound {
        incomeRepository.deleteByIdAndUser(id, getUser(userModel.getId()));
        log.info("Доход удалён успешно");
        return "Доход удалён успешно";
    }
}
