package com.deepak.cmsapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deepak.cmsapp.entities.Role;
import com.deepak.cmsapp.entities.CustomUser;
import com.deepak.cmsapp.exceptions.ResourceNotFoundException;
import com.deepak.cmsapp.payloads.UserDto;
import com.deepak.cmsapp.repositories.RoleRepo;
import com.deepak.cmsapp.repositories.UserRepo;
import com.deepak.cmsapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;


    @Override
    public UserDto createUser(UserDto userDto) {
        CustomUser user = this.dtoToUser(userDto);
        CustomUser savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        CustomUser user = this.userRepo.findById(userId)
                    .orElseThrow(() -> 
                    new ResourceNotFoundException("User", 
                    " Id ", userId));
        
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        CustomUser updatedUser = this.userRepo.save(user);
        UserDto updatedUserDto = this.userToDto(updatedUser);
        return updatedUserDto;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        CustomUser user = this.userRepo.findById(userId)
        .orElseThrow(() -> 
        new ResourceNotFoundException("User", 
        " Id ", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        
        List<CustomUser> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        CustomUser user = this.userRepo.findById(userId)
        .orElseThrow(() -> 
        new ResourceNotFoundException("User", 
        " Id ", userId));

        this.userRepo.delete(user);
        
    }

    private CustomUser dtoToUser(UserDto userDto){
        CustomUser user = this.modelMapper.map(userDto, CustomUser.class);
        return user;
    }

    private UserDto userToDto(CustomUser user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        CustomUser user = this.modelMapper.map(userDto, CustomUser.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Role role = this.roleRepo.findById(2).get();

        user.getRoles().add(role);
        
        CustomUser newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }
    
}
