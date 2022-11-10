package com.deepak.cmsapp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deepak.cmsapp.entities.Role;
import com.deepak.cmsapp.repositories.RoleRepo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class CmsappApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CmsappApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.print(this.passwordEncoder.encode("avinash121"));

		try {
			Role role = new Role();
			role.setId(1);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(2);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			roles.add(role1);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r-> {
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
