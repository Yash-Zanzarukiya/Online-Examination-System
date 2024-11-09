package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Roles;
import com.yashpz.examination_system.examination_system.dto.AuthDTO;
import com.yashpz.examination_system.examination_system.dto.LoginDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.repository.AuthRepository;
import com.yashpz.examination_system.examination_system.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(AuthRepository userRepository, EmailService emailService, JwtUtil jwtUtil, UserService userService) {
        this.authRepository = userRepository;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public void registerUser(AuthDTO user) {
        if(!isUsernameAvailable(user.getUsername()))
            throw new ApiError(HttpStatus.BAD_REQUEST, "Username Already Taken");

        Auth existingAuth = getAuthByEmail(user.getEmail());

        if(existingAuth != null) {
            if (existingAuth.isVerified())
                throw new ApiError(HttpStatus.BAD_REQUEST, "User Already Exists");
            else {
                existingAuth.setUsername(user.getUsername());
                existingAuth.setPassword(passwordEncoder.encode(user.getPassword()));
                saveAuth(existingAuth);
                sendVerificationEmail(existingAuth);
                throw new ApiError(HttpStatus.BAD_REQUEST, "Verification Email Sent Again");
            }
        }
        else{
            Auth newUser = new Auth();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setVerified(false);
            saveAuth(newUser);
            sendVerificationEmail(newUser);
        }
    }

    public Auth loginUser(LoginDTO user) {
        Auth auth = authRepository.findByUsernameOrEmail(user.getIdentifier(), user.getIdentifier());

        if(auth == null)
            throw new ApiError(HttpStatus.BAD_REQUEST, "User Not Found");
        else if (!auth.isVerified())
            throw new ApiError(HttpStatus.BAD_REQUEST, "Email Not Verified");

        if(!passwordEncoder.matches(user.getPassword(), auth.getPassword()))
            throw new ApiError(HttpStatus.BAD_REQUEST, "Invalid Password");

        return auth;
    }

    public void verifyUser(String token) {
        Auth auth = jwtUtil.validateUserFromToken(token);
        if(auth == null)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Invalid User Token");

        if (auth.isVerified())
            throw new ApiError(HttpStatus.BAD_REQUEST, "User Already Verified");

        auth.setVerified(true);
        saveAuth(auth);

        User user = new User();
        user.setUsername(auth.getUsername());
        user.setRole(Roles.STUDENT);
        userService.saveUser(user);
    }

    public boolean isUsernameAvailable(String username) {
        Auth authByUsername = getAuthByUsername(username);

        if(authByUsername==null)
            return true;
        else if (authByUsername.isVerified())
            return false;
        else if(authByUsername.getVerificationEmailSentAt() != null) {
            if (authByUsername.getVerificationEmailSentAt().isAfter(LocalDateTime.now()))
                return false;
            else {
                authRepository.delete(authByUsername);
                return true;
            }
        }
        else
            return true;
    }

    public Auth getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getAuthByUsername(username);
    }

    public Auth getAuthByUsernameOrEmail(String username, String email) {
            return authRepository.findByUsernameOrEmail(username,email);
        }

    public Auth getAuthByUsername(String username) {
        return authRepository.findByUsername(username);
    }

    public Auth getAuthByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    public Auth saveAuth(Auth auth) {
        return authRepository.save(auth);
    }

    public void sendVerificationEmail(Auth auth) {
            String token = jwtUtil.generateToken(auth.getUsername());
            emailService.sendMail(auth.getEmail(), "Verification Email", "Click on the link to verify your email: http://localhost:8080/auth/verify?token=" + token);
            auth.setVerificationEmailSentAt(LocalDateTime.now());
            saveAuth(auth);
        }
}
