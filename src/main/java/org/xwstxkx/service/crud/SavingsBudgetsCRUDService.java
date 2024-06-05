package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.SavingsBudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.model.SavingsBudgetModel;
import org.xwstxkx.repository.SavingsBudgetRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SavingsBudgetsCRUDService {


    private final SavingsBudgetRepository savingsBudgetRepository;
    private final UserService userService;

    UserEntity getUser() {
        return userService.getCurrentUser();
    }

    public String saveSavingsBudget(SavingsBudgetModel model) throws BadCredentials {
        SavingsBudgetEntity entity = SavingsBudgetModel.toEntity(model);
        if (entity.getUser() != null && entity.getUser() != getUser()) {
            throw new BadCredentials();
        } else {
            entity.setUser(getUser());
            savingsBudgetRepository.save(entity);
            log.info("Накопительный бюджет сохранён успешно");
            return "Накопительный бюджет сохранён успешно";
        }
    }

    public SavingsBudgetModel getSavingsBudget(Long id) {
        SavingsBudgetEntity savingsBudgetEntity = savingsBudgetRepository
                .findByIdAndUser(id, getUser());
        log.info("Накопительный бюджет был найден");
        return SavingsBudgetModel.toModel(savingsBudgetEntity);
    }

    public List<SavingsBudgetModel> getAllSavingBudgets() {
        List<SavingsBudgetEntity> savingsBudgetEntities = savingsBudgetRepository
                .findAllByUser(getUser());
        log.info("Все накопительные бюджеты были успешно найдены");
        return SavingsBudgetModel.toListModel(savingsBudgetEntities);
    }

    @Transactional
    public String deleteSavingsBudget(Long id) {
        savingsBudgetRepository.deleteByIdAndUser(id, getUser());
        log.info("Накопительный бюджет удалён успешно");
        return "Накопительный бюджет удалён успешно";
    }
}
