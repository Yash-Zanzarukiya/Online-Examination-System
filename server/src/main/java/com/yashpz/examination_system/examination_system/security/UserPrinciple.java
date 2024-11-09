package com.yashpz.examination_system.examination_system.security;

import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.repository.AuthRepository;
import com.yashpz.examination_system.examination_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrinciple implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public UserPrinciple(UserRepository userRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authRepository.findByUsername(username);
        if (auth == null)
            throw new UsernameNotFoundException("Auth Not Found with username: " + username);
        else if (!auth.isVerified())
            throw new UsernameNotFoundException("User is Not Verified");

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User Not Found with username: " + username);

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(auth.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}
