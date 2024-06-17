package org.xwstxkx.mail.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.xwstxkx.mail.MailModel;

@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${service.activation.uri}")
    private String activationServiceUri;

    @Override
    public void send(MailModel mailModel) {
        var subject = "Активация учётной записи";
        var messageBody = getActivationMailBody(mailModel.getId());
        var emailTo = mailModel.getEmailTo();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText((String) messageBody);

        javaMailSender.send(mailMessage);
    }

    private Object getActivationMailBody(String id) {
        String message = String.format("Для завершения регистрации" +
                        " перейдите по ссылке: \n%s", activationServiceUri
                );
        return message.replace("{id}", id);
    }
}
