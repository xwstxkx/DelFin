package org.xwstxkx.mailservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailParams {
    private Long id;
    private String emailTo;
}
