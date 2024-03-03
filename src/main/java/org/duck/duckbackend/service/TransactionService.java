package org.duck.duckbackend.service;

import lombok.AllArgsConstructor;
import org.duck.duckbackend.entity.TransactionEntity;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.model.TransactionModel;
import org.duck.duckbackend.repository.TransactionRepository;
import org.duck.duckbackend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public void transactionSave(TransactionModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity userEntity = userRepository.findByUsername(currentPrincipalName);
        TransactionEntity transactionEntity = TransactionModel.toEntity(model);
        transactionEntity.setUser(userEntity);
        transactionRepository.save(transactionEntity);
    }
}
