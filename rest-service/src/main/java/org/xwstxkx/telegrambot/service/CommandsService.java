package org.xwstxkx.telegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.util.Role;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommandsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String startCommand(String username, Long userId) {
        var text = """
                Добро пожаловать в бот, %s!
                                
                Здесь Вы сможете управлять своими финансами.
                                
                Для этого введите пожалуйста свою электронную почту:
                ...
                                
                Дополнительные команды:
                /help - получение справки
                """;
        registerTgUser(username, userId);
        return String.format(text, username);
    }

    public String helpCommand() {
        var text = """
                Запуск бота:
                /start - с дальнейшей регистрацией (подтверждение через email);
                                
                Что бот умеет:
                /budgets - операции над бюджетами, а именно:
                ✅ Создание
                ✅ Получение
                ✅ Изменение
                ✅ Удаление
                """;
        return String.format(text);
    }

    private void registerTgUser(String username, Long userId) {
        if (username == "null") {
            String usernameTg = username;
            Long i = 0L;
            while (userRepository.existsByUsername(username)) {
                username = usernameTg + i.toString();
                i++;
            }
        }
        if (!userRepository.existsByUsername(username)) {
            userRepository.save(UserEntity.builder()
                    .username(username)
                    .password(passwordEncoder.encode("r@wP@SSw0rd"
                            + userId.toString() + "2st0ng"))
                    .role(Role.ROLE_USER)
                    .email(username + "@gmail.com")
                    .build());
            log.info("Пользователь с ником " + username + " успешно создан");
        }
    }
}
