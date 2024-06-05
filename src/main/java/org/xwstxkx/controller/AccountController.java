package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CheckModel;
import org.xwstxkx.model.MoneyModel;
import org.xwstxkx.service.AccountService;
import org.xwstxkx.service.CheckService;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/account/")
@AllArgsConstructor
public class AccountController {

    private final CheckService checkService;
    private final AccountService accountService;

    @GetMapping("/check/{category_id}/{budget_id}")
    @Operation(summary = "Вычисление итоговой суммы по чеку")
    public Long checkFind(@RequestBody CheckModel checkModel,
                          @PathVariable Long category_id,
                          @PathVariable Long budget_id)
            throws MalformedURLException, ObjectNotFound {
        return checkService.checkTransaction(checkModel, category_id, budget_id);
    }

    @GetMapping("/countAllMoney")
    @Operation(summary = "Подсчёт всех денег")
    public MoneyModel countAllMoney() {
        return accountService.countAllMoney();
    }
}