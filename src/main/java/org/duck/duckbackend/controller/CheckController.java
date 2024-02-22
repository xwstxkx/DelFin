package org.duck.duckbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.duck.duckbackend.service.CheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/check")
    @Operation(summary = "Вычисление итоговой суммы по чеку")
    public String checkFind(@RequestParam String date,
                            @RequestParam String uid)
            throws MalformedURLException {
        return checkService.checkFind(date, uid);
    }
}