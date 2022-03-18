package com.example.laptop.service;

import com.example.laptop.db.entity.Cart;
import com.example.laptop.db.entity.User;
import com.example.laptop.db.mapper.UserMapper;
import com.example.laptop.db.model.JwtModel;
import com.example.laptop.db.model.LoginModel;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.model.UserModel;
import com.example.laptop.db.repository.CartRepository;
import com.example.laptop.db.repository.UserRepository;
import com.example.laptop.mail.MailService;
import com.example.laptop.security.jwt.JwtUtils;
import com.example.laptop.security.service.UserDetailsImpl;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public ResponseEntity<?> create(UserModel userModel) {
        if (userRepository.existsByEmail(userModel.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, MessageConstants.ACCOUNT_ALREADY_EXISTS);
        }
        User user = new User();
        user.setEmail(userModel.getEmail().toLowerCase());
        user.setName(userModel.getName());
        user.setPassword(encoder.encode(userModel.getPassword()));
        user.setAdmin(false);
        user.setActive(false);
        user = userRepository.saveAndFlush(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        String activeCode = null;
        do {
            activeCode = UUID.randomUUID().toString();
        } while (!createCodeFile(activeCode, userModel.getEmail()));
        mailService.sendConfirmMail(emailSender, userModel.getEmail(), activeCode);

        return new ResponseEntity<>(new ModelPage<>(userMapper.toModel(user)), HttpStatus.OK);
    }

    public ResponseEntity<?> active(String token, String email) {
        email = email.toLowerCase();
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        File file = new File(Constants.CODE_FOLDER + "/" + token + "_" + email);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        user.setActive(true);
        userRepository.save(user);
        file.delete();

        return new ResponseEntity<>( HttpStatus.OK);
    }

    public ResponseEntity<?> login(LoginModel loginModel) {
        loginModel.setEmail(loginModel.getEmail().toLowerCase());

        if (!userRepository.existsByEmailAndActiveTrue(loginModel.getEmail())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            JwtModel jwtResponse = new JwtModel(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getIsAdmin());

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } catch (BadCredentialsException badCredentialsException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, MessageConstants.WRONG_PASSWORD);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findByIdAndActiveTrue(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        if (encoder.matches(oldPassword, user.getPassword())) {
            user.setUpdatedDate(new Date());
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);

            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, MessageConstants.WRONG_PASSWORD);
        }
    }

    public ResponseEntity<?> forgotPassword(String email) {
        if (userRepository.existsByEmailAndActiveTrue(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND);
        }
        String token = UUID.randomUUID().toString();
        mailService.sendConfirmMail(emailSender, email, token);
        String code = null;
        do {
            code = UUID.randomUUID().toString();
        } while (!createCodeFile(code, email));
        mailService.sendForgotPasswordMail(emailSender, email, code);

        return new ResponseEntity<>( HttpStatus.OK);
    }

    public ResponseEntity<?> resetPassword(String email, String code, String password) {
        email = email.toLowerCase();
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        File file = new File(Constants.CODE_FOLDER + "/" + code + "_" + email);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        user.setUpdatedDate(new Date());
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        file.delete();

        return new ResponseEntity<>( HttpStatus.OK);
    }

    public boolean createCodeFile(String token, String email) {
        try {
            File myObj = new File(Constants.CODE_FOLDER + "/" + token + "_" + email);
            return myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }
}
