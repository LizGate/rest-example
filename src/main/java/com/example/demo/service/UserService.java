package com.example.demo.service;


import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.authentication.UserPrinciple;

import com.example.demo.token.confirmation.ConfirmationToken;
import com.example.demo.token.confirmation.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("username not found: " + username)
                );

        return UserPrinciple.valueOf(user);

    }


    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        UserPrinciple user = (UserPrinciple) userDetails;

        RoleEntity role = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Role id bulunamadi"));

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setAddress(user.getAddress());
        userEntity.setBusiness_address(user.getBusiness_address());
        userEntity.setBusiness_name(user.getBusiness_name());
        userEntity.setRoles(Set.of(role));
        userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),userEntity);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
            mimeMessageHelper.setText(buildEmail(user.getUsername(),"http://localhost:8080/api/v1/account/confirm?activate="+token),true);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("Confirm your account");
            mimeMessageHelper.setFrom("no-reply@yurtbay.com");
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e){
            throw new IllegalStateException("failed to send mail");
        }

    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()-> new IllegalStateException("token bulunamadi"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("hesap zaten onaylanmis.");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableAppUser(confirmationToken.getUserEntity().getEmail());
        return "confirmed";
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
    }

    @Override
    public void deleteUser(String s) {
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }


    private String buildEmail(String name, String link){
        return name+" Onay linkiniz: "+link;
    }


    public String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();
        return currentName;
    }

}
