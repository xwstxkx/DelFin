package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.model.entity.DebtsModel;
import org.xwstxkx.service.RestUserService;
import org.xwstxkx.service.crud.DebtsCRUDService;
import org.xwstxkx.user.RestUser;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Долги")
@RequestMapping("/debts")
public class DebtsController {

    private final DebtsCRUDService debtsCRUDService;
    private final RestUserService restUserService;

    private RestUser getUser() {
        return restUserService.getCurrentUser();
    }

    @GetMapping("/getDebt/{id}")
    @Operation(summary = "Нахождение долга")
    public ResponseEntity<DebtsModel> getDebt(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(debtsCRUDService.getDebt(id , UserModel.toModel(getUser())));
    }

    @PostMapping("/saveDebt")
    @Operation(summary = "Сохранение долга")
    public String saveDebt(@RequestBody DebtsModel debtModel)
            throws BadCredentials, ObjectNotFound {
        return debtsCRUDService.saveDebt(debtModel , UserModel.toModel(getUser()));
    }

    @PutMapping("/changeDebt")
    @Operation(summary = "Сохранение долга")
    public String changeDebt(@RequestBody DebtsModel debtModel)
            throws BadCredentials, ObjectNotFound {
        return debtsCRUDService.saveDebt(debtModel , UserModel.toModel(getUser()));
    }

    @DeleteMapping("/deleteDebt/{id}")
    @Operation(summary = "Удаление долга")
    public String saveDebt(@PathVariable Long id) throws ObjectNotFound {
        return debtsCRUDService.deleteDebt(id , UserModel.toModel(getUser()));
    }

    @GetMapping("/getAllDebts")
    @Operation(summary = "Получение всех долгов")
    public ResponseEntity<List<DebtsModel>> getAllDebts() throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(debtsCRUDService.getAllDebts(UserModel.toModel(getUser())));
    }
}
