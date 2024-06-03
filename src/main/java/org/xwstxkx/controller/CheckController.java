package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CheckModel;
import org.xwstxkx.service.CheckService;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/check/{category_id}")
    @Operation(summary = "Вычисление итоговой суммы по чеку")
    public Long checkFind(@RequestBody CheckModel checkModel,
                          @PathVariable Long category_id)
            throws MalformedURLException, ObjectNotFound {
        return checkService.checkTransaction(checkModel, category_id);
    }
}