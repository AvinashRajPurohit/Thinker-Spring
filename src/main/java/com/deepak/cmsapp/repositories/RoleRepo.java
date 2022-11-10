package com.deepak.cmsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.cmsapp.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
    
}
