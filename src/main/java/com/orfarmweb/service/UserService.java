package com.orfarmweb.service;

import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.PasswordDTO;

public interface UserService {
    boolean registerUser(User user);
    boolean checkExist(String email);
    User getCurrentUser();

    User updateUser(int id, User userRequest);
    User findById(int id);
    boolean updatePassword(PasswordDTO passwordDTO);
}
