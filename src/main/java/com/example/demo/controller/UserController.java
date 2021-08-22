package com.example.demo.controller;

import com.example.demo.controller.dto.AccountCredential;
import com.example.demo.entity.UserEntity;
import com.example.demo.security.authentication.UserPrinciple;
import com.example.demo.security.jwt.JwtService;

import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/account")
@Api(value = "User Api Documentation")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserDetailsManager userDetailsManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @PostMapping("signup/customer")
    @ApiOperation(value = "Customer register method")
    public ResponseEntity<String> createAccountCustomer(@RequestBody AccountCredential accountCredential) {

        if (userDetailsManager.userExists(accountCredential.getUsername())) {
            return ResponseEntity.status(409).body("username is exited");
        }

        UserPrinciple newUser = new UserPrinciple(
                null,
                accountCredential.getUsername(),
                accountCredential.getEmail(),
                accountCredential.getPassword(),
                accountCredential.getAddress(),
                null,
                null,
                null
        );

        userDetailsManager.createUser(newUser);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("signup/seller")
    @ApiOperation(value = "Seller register method")
    public ResponseEntity<String> createAccountSeller(@RequestBody AccountCredential accountCredential) {

        // check if user exists
        if (userDetailsManager.userExists(accountCredential.getUsername())) {
            return ResponseEntity.status(409).body("username is exited");
        }
        // In this example, the userDetailsManager.createUser will allocate the new user as a ROLE_USER
        // You can re-implement this in what you want
        UserPrinciple newUser = new UserPrinciple(
                null, // db will allocate a id, not to specify here
                accountCredential.getUsername(),
                accountCredential.getEmail(),
                accountCredential.getPassword(),
                null,
                accountCredential.getBusiness_address(),
                accountCredential.getBusiness_name(),
                null
        );

        userDetailsManager.createUser(newUser);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("login")
    @ApiOperation(value = "Login method")
    public ResponseEntity<Map<String, String>> login(@RequestBody AccountCredential loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                ));

        return ResponseEntity.ok(Map.of("token", jwtService.generateUserToken(authentication)));
    }

    @GetMapping("confirm")
    @ApiOperation(value = "Account mail confirm token method")
    public ResponseEntity<String> confirm(@RequestParam("activate") String token){

        userService.confirmToken(token);
        return ResponseEntity.ok("Onaylandi.");
    }

    @GetMapping("list")
    @ApiOperation(value = "List all accounts method")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }


}
