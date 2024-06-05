package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель выдачи показателей бюджета")
public class MoneyModel {

    private Long total;
    private Long spent;
    private Long balance;
}