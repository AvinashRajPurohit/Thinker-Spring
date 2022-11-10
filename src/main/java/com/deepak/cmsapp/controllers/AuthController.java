package com.deepak.cmsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.cmsapp.exceptions.ApiException;
import com.deepak.cmsapp.payloads.JWTAuthRequest;
import com.deepak.cmsapp.payloads.JWTAuthResponse;
import com.deepak.cmsapp.payloads.UserDto;
import com.deepak.cmsapp.security.JWTTokenHelper;
import com.deepak.cmsapp.services.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(
        @RequestBody JWTAuthRequest request
    ) throws Exception{
        System.out.println("helllo i am here...");
        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JWTAuthResponse response = new JWTAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception{
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            this.authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid Username or Password");
        }
    }
    

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){

        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);

    }
}
