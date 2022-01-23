package com.orfarmweb.service;

import com.orfarmweb.entity.User;

public interface UserService {
    boolean registerUser(User user);
    boolean checkExist(String email);
}
