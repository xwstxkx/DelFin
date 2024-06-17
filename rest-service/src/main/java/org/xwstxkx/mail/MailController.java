package org.xwstxkx.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xwstxkx.mail.mail.SenderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final SenderService senderService;

    @PostMapping("/send")
    public ResponseEntity<?> sendActivationMail(@RequestBody MailModel mailModel) {
        senderService.send(mailModel);
        return ResponseEntity.ok().build();
    }
}
