package org.xwstxkx.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.service.UserActivationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class ActivationController {
    private final UserActivationService userActivationService;

    @GetMapping("/activation")
    public ResponseEntity<?> activation(@RequestParam("id") Long id) throws ObjectNotFound {
        var res = userActivationService.activation(id);
        if (res) {
            return ResponseEntity.ok().body("Регистрация успешно завершена!");
        }
        return ResponseEntity.internalServerError().build();
    }
}
