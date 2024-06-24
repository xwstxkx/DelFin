package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.DebtEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.model.entity.DebtsModel;
import org.xwstxkx.repository.DebtsRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DebtsCRUDService {

    private final DebtsRepository debtsRepository;
    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }


    public String saveDebt(DebtsModel model, UserModel userModel)
            throws BadCredentials, ObjectNotFound {
        DebtEntity debt = DebtsModel.toEntity(model);
        if (debt.getUser() != null && debt.getUser() != getUser(userModel.getId())) {
            throw new BadCredentials();
        } else {
            DebtEntity entity = DebtsModel.toEntity(model);
            entity.setUser(getUser(userModel.getId()));
            debtsRepository.save(entity);
            log.info("Долг сохранён успешно");
            return "Долг сохранён успешно";
        }
    }

    public DebtsModel getDebt(Long id, UserModel userModel) throws ObjectNotFound {
        DebtEntity debt = debtsRepository.findByIdAndUser(id, getUser(userModel.getId()));
        log.info("Долг был найден");
        return DebtsModel.toModel(debt);
    }

    public List<DebtsModel> getAllDebts(UserModel userModel) throws ObjectNotFound {
        List<DebtEntity> debts = debtsRepository.findAllByUser(getUser(userModel.getId()));
        log.info("Все долги были успешно найдены");
        return DebtsModel.toListModel(debts);
    }

    @Transactional
    public String deleteDebt(Long id, UserModel userModel) throws ObjectNotFound {
        debtsRepository.deleteByIdAndUser(id, getUser(userModel.getId()));
        log.info("Долг удалён успешно");
        return "Долг удалён успешно";
    }
}
