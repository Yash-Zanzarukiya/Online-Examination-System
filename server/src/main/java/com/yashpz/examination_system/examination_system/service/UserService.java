package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


}
