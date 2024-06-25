package org.xwstxkx.service;

import org.xwstxkx.entity.UserEntity;

public interface UserService {
    String registerUser(UserEntity userEntity);
    String setEmail(UserEntity userEntity, String email);
}
