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
            if(isUserExists(user.getUsername(), user.getEmail()))
                throw new ApiError(HttpStatus.BAD_REQUEST, "User Already Exists");

            Auth newUser = new Auth();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setVerified(false);
            saveAuth(newUser);

            sendVerificationEmail(newUser);
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
            String username = jwtUtil.extractUsername(token);

            if(!jwtUtil.validateToken(token) || username == null || username.isEmpty())
                throw new ApiError(HttpStatus.BAD_REQUEST, "Invalid Token");

            Auth auth = getAuthByUsername(username);
            if(auth == null)
                throw new ApiError(HttpStatus.BAD_REQUEST, "User Not Found");

            auth.setVerified(true);
            saveAuth(auth);

            User user = new User();
            user.setUsername(auth.getUsername());
            user.setRole(Roles.STUDENT);
            userService.saveUser(user);
        }

        public boolean isUserExists(String username, String email) {
            return authRepository.findByUsernameOrEmail(username,email) != null;
        }

        public Auth getAuthByUsername(String username) {
            return authRepository.findByUsername(username);
        }

        public Auth getAuthByEmail(String email) {
            return authRepository.findByEmail(email);
        }

        public void sendVerificationEmail(Auth auth) {
            emailService.sendMail(auth.getEmail(), "Verification Email", "Click on the link to verify your email");
            auth.setVerificationEmailSentAt(LocalDateTime.now());
            saveAuth(auth);
        }

        public Auth saveAuth(Auth auth) {
            return authRepository.save(auth);
        }
}
