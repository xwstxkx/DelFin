package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.IncomeEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.IncomeModel;
import org.xwstxkx.repository.CategoryRepository;
import org.xwstxkx.repository.IncomeRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class IncomesCRUDService {


    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    UserEntity getUser() {
        return userService.getCurrentUser();
    }

    public String saveIncome(IncomeModel model, Long category_id)
            throws ObjectNotFound, BadCredentials {
        IncomeEntity entity = IncomeModel.toEntity(model);
        if (entity.getUser() != null && entity.getUser() != getUser()) {
            throw new BadCredentials();
        } else {
            entity.setUser(getUser());
            entity.setCategory(categoryRepository
                    .findById(category_id)
                    .orElseThrow(ObjectNotFound::new));
            incomeRepository.save(entity);
            log.info("Доход сохранён успешно");
            return "Доход сохранён успешно";
        }
    }

    public IncomeModel getIncome(Long id){
        IncomeEntity income = incomeRepository
                .findByIdAndUser(id, getUser());
        log.info("Доход был найден");
        return IncomeModel.toModel(income);
    }

    public List<IncomeModel> getAllIncomes() {
        List<IncomeEntity> incomes = incomeRepository
                .findAllByUser(getUser());
        log.info("Все доходы были успешно найдены");
        return IncomeModel.toListModel(incomes);
    }

    @Transactional
    public String deleteIncomes(Long id) {
        incomeRepository.deleteByIdAndUser(id, getUser());
        log.info("Доход удалён успешно");
        return "Доход удалён успешно";
    }
}
