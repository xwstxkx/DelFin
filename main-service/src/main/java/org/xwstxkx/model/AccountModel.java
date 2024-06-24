package org.xwstxkx.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель отчёта")
public class AccountModel {

    @Schema(description = "Название отчёта", example = "Отчёт за май 2024")
    @NotBlank(message = "Название отчёта не может быть пустым")
    private String title;
    @Schema(description = "Получено прибылью", example = "10000")
    @NotBlank(message = "Прибыль не должна быть пустой")
    private Long total;
    @Schema(description = "Потрачено", example = "10000")
    private Long spent;
    @Schema(description = "Итоговый баланс", example = "10000")
    @NotBlank(message = "Баланс не может быть пустым")
    private Long balance;
}
