package org.xwstxkx.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель сканирования чека")
public class CheckModel {

    private String uid;
    private LocalDate date;

}
