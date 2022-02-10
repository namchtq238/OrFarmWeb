package com.orfarmweb.service.serviceimp;

import com.orfarmweb.constaint.Role;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.UserDTO;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.security.CustomUserDetails;
import com.orfarmweb.service.UserService;
import lombok.SneakyThrows;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    @Override
    public boolean registerUser(@Valid User user) {
        if(checkExist(user.getEmail())) throw new MessageDescriptorFormatException("Đã Tồn Tại Email");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean checkExist(String email) {
        for(String s : userRepo.getEmail()){
            if(email.equalsIgnoreCase(s)) return true;
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    @Override
    public boolean saveUser(User user) {
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean saveUserById(UserDTO userDTO, int id) {
        userRepo.saveUserByEmail(userDTO.getFirstName(), userDTO.getLastName(), id);
        return true;
    }

    @Override
    public User updateUser(int id, User userRequest) {
        User user = userRepo.findById(id).get();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        return userRepo.save(user);
    }

    @Override
    public boolean updatePassword(String password) {
        User user = getCurrentUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        return true;
    }


}
