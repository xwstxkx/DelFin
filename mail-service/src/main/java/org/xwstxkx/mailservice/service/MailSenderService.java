package org.xwstxkx.mailservice.service;

import org.xwstxkx.mailservice.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
