package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.LoanEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.entity.LoanModel;
import org.xwstxkx.repository.LoanRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LoansCRUDService {

    private final LoanRepository loanRepository;

    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public String saveLoan(LoanModel model, UserModel userModel)
            throws BadCredentials, ObjectNotFound {
        LoanEntity loan = LoanModel.toEntity(model);
        if (loan.getUser() != null && loan.getUser() != getUser(userModel.getId())) {
            throw new BadCredentials();
        } else {
            LoanEntity entity = LoanModel.toEntity(model);
            entity.setUser(getUser(userModel.getId()));
            loanRepository.save(entity);
            log.info("Займ сохранён успешно");
            return "Займ сохранён успешно";
        }
    }

    public LoanModel getLoan(Long id, UserModel userModel) throws ObjectNotFound {
        LoanEntity loan = loanRepository.findByIdAndUser(id, getUser(userModel.getId()));
        log.info("Займ был найден");
        return LoanModel.toModel(loan);
    }

    public List<LoanModel> getAllLoans(UserModel userModel) throws ObjectNotFound {
        List<LoanEntity> loan = loanRepository.findAllByUser(getUser(userModel.getId()));
        log.info("Все займы были успешно найдены");
        return LoanModel.toListModel(loan);
    }

    @Transactional
    public String deleteLoan(Long id, UserModel userModel) throws ObjectNotFound {
        loanRepository.deleteByIdAndUser(id, getUser(userModel.getId()));
        log.info("Займ удалён успешно");
        return "Займ удалён успешно";
    }
}