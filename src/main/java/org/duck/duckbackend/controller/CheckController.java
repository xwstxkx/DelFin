package org.duck.duckbackend.controller;

import lombok.AllArgsConstructor;
import org.duck.duckbackend.service.CheckService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/check")
    public String checkFind(@RequestParam String date,
                         @RequestParam String uid) {
        return checkService.checkFind(date, uid);
    }
}