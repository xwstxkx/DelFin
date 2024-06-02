package org.xwstxkx.service.crud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.ExpenseEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.ExpenseModel;
import org.xwstxkx.repository.CategoryRepository;
import org.xwstxkx.repository.ExpenseRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ExpensesCRUDService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    UserEntity getUser() {
        return userService.findUser();
    }

    public String saveExpense(ExpenseModel model, Long category_id)
            throws ObjectNotFound, BadCredentials {
        ExpenseEntity entity = ExpenseModel.toEntity(model);
        if (entity.getUser() != null && entity.getUser() != getUser()) {
            throw new BadCredentials();
        } else
            entity.setUser(getUser());
        entity.setCategory(categoryRepository
                .findById(category_id)
                .orElseThrow(ObjectNotFound::new));
        expenseRepository.save(entity);
        log.info("Расход сохранён успешно");
        return "Расход сохранён успешно";
    }


    public ExpenseModel getExpense(Long id) {
        ExpenseEntity expense = expenseRepository
                .findByIdAndUser(id, getUser());
        log.info("Расход был найден");
        return ExpenseModel.toModel(expense);
    }

    public List<ExpenseModel> getAllExpenses() {
        List<ExpenseEntity> expenses = expenseRepository
                .findAllByUser(getUser());
        log.info("Все расходы были успешно найдены");
        return ExpenseModel.toListModel(expenses);
    }

    public String deleteExpense(Long id) {
        expenseRepository.deleteByIdAndUser(id, getUser());
        log.info("Расход удалён успешно");
        return "Расход удалён успешно";
    }
}
