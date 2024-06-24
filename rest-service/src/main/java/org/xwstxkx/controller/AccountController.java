package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.AccountModel;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.model.entity.CheckModel;
import org.xwstxkx.service.AccountService;
import org.xwstxkx.service.CheckService;
import org.xwstxkx.service.RestUserService;
import org.xwstxkx.user.RestUser;

import java.net.MalformedURLException;
import java.time.LocalDate;


@RestController
@AllArgsConstructor
@RequestMapping("/account/")
@Tag(name = "Контроллер подсчёта и анализа")
public class AccountController {

    private final CheckService checkService;
    private final AccountService accountService;
    private final RestUserService restUserService;

    private RestUser getUser() {
        return restUserService.getCurrentUser();
    }

    @GetMapping("/check/{category_id}/{budget_id}")
    @Operation(summary = "Вычисление итоговой суммы по чеку")
    public Long checkFind(@RequestBody CheckModel checkModel,
                          @PathVariable Long category_id,
                          @PathVariable Long budget_id)
            throws MalformedURLException, ObjectNotFound {
        return checkService.checkTransaction(checkModel, category_id,
                budget_id, UserModel.toModel(getUser()));
    }

    @GetMapping("/countAllCategoryExpense/{periodBegin}/{periodEnd}/{id}")
    @Operation(summary = "Подсчёт всех затрат в категории")
    public Long countAllCategoryExpense(@PathVariable LocalDate periodBegin,
                                        @PathVariable LocalDate periodEnd,
                                        @PathVariable Long id) throws ObjectNotFound {
        return accountService.allCategoryExpenses(periodBegin, periodEnd, id
                , UserModel.toModel(getUser()));
    }

    @GetMapping("/accountExpensesAndIncomesInPeriod/{periodBegin}/{periodEnd}/{title}")
    @Operation(summary = "Подсчёт всех доходов и расходов за период")
    public AccountModel countAllCategoryExpense(@PathVariable LocalDate periodBegin,
                                                @PathVariable LocalDate periodEnd,
                                                @PathVariable String title) throws ObjectNotFound {
        return accountService.accountExpensesAndIncomesInPeriod(periodBegin, periodEnd, title,
                UserModel.toModel(getUser()));
    }
}