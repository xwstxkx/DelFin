package org.xwstxkx.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.xwstxkx.mailservice.dto.MailParams;
import org.xwstxkx.mailservice.service.MailSenderService;

@Log4j2
@RequiredArgsConstructor
@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;


    @Override
    public void send(MailParams mailParams) {
        var subject = "Активация учетной записи";
        var messageBody = getActivationMailBody(mailParams.getId().toString());
        var emailTo = mailParams.getEmailTo();
        log.debug(String.format("Sending email for mail=[%s]", emailTo));

        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("xwstxkx@yandex.by");
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        javaMailSender.send(mailMessage);
    }

    private String getActivationMailBody(String id) {
        var msg = String.format("Для завершения регистрации перейдите по ссылке:\n%s",
                "http://127.0.0.1:8086/mail/activation?id={id}");
        return msg.replace("{id}", id);
    }
}
