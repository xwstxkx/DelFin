package org.xwstxkx.mail;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailModel {
    private String id;
    private String emailTo;
}
