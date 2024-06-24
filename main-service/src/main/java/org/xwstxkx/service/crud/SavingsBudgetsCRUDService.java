package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.SavingsBudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.model.entity.SavingsBudgetModel;
import org.xwstxkx.repository.SavingsBudgetRepository;
import org.xwstxkx.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SavingsBudgetsCRUDService {


    private final SavingsBudgetRepository savingsBudgetRepository;
    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public String saveSavingsBudget(SavingsBudgetModel model, UserModel userModel)
            throws BadCredentials, ObjectNotFound {
        SavingsBudgetEntity entity = SavingsBudgetModel.toEntity(model);
        if (entity.getUser() != null && entity.getUser() != getUser(userModel.getId())) {
            throw new BadCredentials();
        } else {
            entity.setUser(getUser(userModel.getId()));
            savingsBudgetRepository.save(entity);
            log.info("Накопительный бюджет сохранён успешно");
            return "Накопительный бюджет сохранён успешно";
        }
    }

    public SavingsBudgetModel getSavingsBudget(Long id, UserModel userModel)
            throws ObjectNotFound {
        SavingsBudgetEntity savingsBudgetEntity = savingsBudgetRepository
                .findByIdAndUser(id, getUser(userModel.getId()));
        log.info("Накопительный бюджет был найден");
        return SavingsBudgetModel.toModel(savingsBudgetEntity);
    }

    public List<SavingsBudgetModel> getAllSavingBudgets(UserModel userModel)
            throws ObjectNotFound {
        List<SavingsBudgetEntity> savingsBudgetEntities = savingsBudgetRepository
                .findAllByUser(getUser(userModel.getId()));
        log.info("Все накопительные бюджеты были успешно найдены");
        return SavingsBudgetModel.toListModel(savingsBudgetEntities);
    }

    @Transactional
    public String deleteSavingsBudget(Long id, UserModel userModel) throws ObjectNotFound {
        savingsBudgetRepository.deleteByIdAndUser(id, getUser(userModel.getId()));
        log.info("Накопительный бюджет удалён успешно");
        return "Накопительный бюджет удалён успешно";
    }
}
