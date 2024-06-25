package org.xwstxkx.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.entity.UserState;
import org.xwstxkx.mailservice.dto.MailParams;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.service.UserService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private String mailServiceUri = "http://127.0.0.1:8087/mail/send";

    @Override
    public String registerUser(UserEntity userEntity) {
        if (userEntity.getIsActive()) {
            return "Вы уже зарегистрированы!";
        } else if (userEntity.getEmail() != null) {
            return "На Вашу почту уже было отправлено письмо. " +
                    "Перейдите по ссылке в письме для завершения регистрации";
        }
        userEntity.setUserState(UserState.WAIT_FOR_EMAIL_STATE);
        userRepository.save(userEntity);
        return "Введите, пожалуйста, Ваш email";
    }

    @Override
    public String setEmail(UserEntity userEntity, String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            return "Введите, пожалуйста, корректный email. Для отмены команды введите /cancel";
        }
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            userEntity.setEmail(email);
            userEntity.setUserState(UserState.BASIC_STATE);
            userEntity = userRepository.save(userEntity);

            var response = sendRequestToMailService(userEntity.getId(), email);
            if (response.getStatusCode() != HttpStatus.OK) {
                var msg = String.format("Отправка электронного письма на почту %s не удалась.", email);
                log.error(msg);
                userEntity.setEmail(null);
                userRepository.save(userEntity);
                return msg;
            }
            return "Вам на почту отправлено письмо, перейдите по ссылке в письме для завершения регистрации.";
        } else {
            return "Этот email уже используется, введите корректный email." +
                    "Для отмены введите /cancel";
        }
    }

    private ResponseEntity<String> sendRequestToMailService(Long id, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(id)
                .emailTo(email)
                .build();
        var request = new HttpEntity<MailParams>(mailParams, headers);
        return restTemplate.exchange(mailServiceUri,
                HttpMethod.POST,
                request,
                String.class);
    }
}
