package com.deepak.cmsapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.cmsapp.entities.CustomUser;

public interface UserRepo extends JpaRepository<CustomUser, Integer> {
    
    Optional<CustomUser> findByEmail(String email);
}
