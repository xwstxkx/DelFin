package org.xwstxkx.telegrambot.service;

import org.springframework.stereotype.Service;

@Service
public class CommandsService {

    public String startCommand(String username) {
        var text = """
                Добро пожаловать в бот, %s!
                                
                Здесь Вы сможете управлять своими финансами.
                                
                Для этого воспользуйтесь командами:
                ...
                                
                Дополнительные команды:
                /help - получение справки
                """;
        return String.format(text, username);
    }
}
