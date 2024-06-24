package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.user.RestUser;

@Slf4j
@Service
@AllArgsConstructor
public class SignUpService {

    private final RestUserService restUserService;

    public RestUser getUser() {
        return restUserService.getCurrentUser();
    }
}
