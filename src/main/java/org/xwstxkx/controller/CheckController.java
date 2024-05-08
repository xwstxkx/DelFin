package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CheckModel;
import org.xwstxkx.service.CheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/check")
    @Operation(summary = "Вычисление итоговой суммы по чеку")
    public Long checkFind(@RequestBody CheckModel checkModel)
            throws MalformedURLException, ObjectNotFound {
        return checkService.checkTransaction(checkModel);
    }
}