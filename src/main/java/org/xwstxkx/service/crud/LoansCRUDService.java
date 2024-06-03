package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.LoanEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.LoanModel;
import org.xwstxkx.repository.LoanRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LoansCRUDService {

    private final LoanRepository loanRepository;
    private final UserService userService;

    UserEntity getUser() {
        return userService.getCurrentUser();
    }


    public String saveLoan(LoanModel model) throws BadCredentials, ObjectNotFound {
        LoanEntity loan = LoanModel.toEntity(model);
        if (loan.getUser() != null && loan.getUser() != getUser()) {
            throw new BadCredentials();
        } else {
            LoanEntity entity = LoanModel.toEntity(model);
            entity.setUser(getUser());
            loanRepository.save(entity);
            log.info("Займ сохранён успешно");
            return "Займ сохранён успешно";
        }
    }

    public LoanModel getLoan(Long id) {
        LoanEntity loan = loanRepository.findByIdAndUser(id, getUser());
        log.info("Займ был найден");
        return LoanModel.toModel(loan);
    }

    public List<LoanModel> getAllLoans() {
        List<LoanEntity> loan = loanRepository.findAllByUser(getUser());
        log.info("Все займы были успешно найдены");
        return LoanModel.toListModel(loan);
    }

    @Transactional
    public String deleteLoan(Long id) {
        loanRepository.deleteByIdAndUser(id, getUser());
        log.info("Займ удалён успешно");
        return "Займ удалён успешно";
    }
}