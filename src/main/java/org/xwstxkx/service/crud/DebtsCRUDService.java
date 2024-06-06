package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.DebtEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.model.entity.DebtsModel;
import org.xwstxkx.repository.DebtsRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DebtsCRUDService {

    private final DebtsRepository debtsRepository;
    private final UserService userService;

    UserEntity getUser() {
        return userService.getCurrentUser();
    }


    public String saveDebt(DebtsModel model) throws BadCredentials {
        DebtEntity debt = DebtsModel.toEntity(model);
        if (debt.getUser() != null && debt.getUser() != getUser()) {
            throw new BadCredentials();
        } else {
            DebtEntity entity = DebtsModel.toEntity(model);
            entity.setUser(getUser());
            debtsRepository.save(entity);
            log.info("Долг сохранён успешно");
            return "Долг сохранён успешно";
        }
    }

    public DebtsModel getDebt(Long id) {
        DebtEntity debt = debtsRepository.findByIdAndUser(id, getUser());
        log.info("Долг был найден");
        return DebtsModel.toModel(debt);
    }

    public List<DebtsModel> getAllDebts() {
        List<DebtEntity> debts = debtsRepository.findAllByUser(getUser());
        log.info("Все долги были успешно найдены");
        return DebtsModel.toListModel(debts);
    }

    @Transactional
    public String deleteDebt(Long id) {
        debtsRepository.deleteByIdAndUser(id, getUser());
        log.info("Долг удалён успешно");
        return "Долг удалён успешно";
    }
}
