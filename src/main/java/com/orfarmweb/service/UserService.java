package com.orfarmweb.service;

import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.UserDTO;

public interface UserService {
    boolean registerUser(User user);
    boolean checkExist(String email);
    User getCurrentUser();
    boolean saveUser(User user);
    boolean saveUserById(UserDTO userDTO, int id);
    User updateUser(int id, User userRequest);
    User findById(int id);
    boolean updatePassword(String password);
}
