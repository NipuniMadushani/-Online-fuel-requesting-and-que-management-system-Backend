package com.lmu.batch18.onlinefuelrequestmanagementsysten;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.ERole;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Role;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.RoleRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityJwtApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (roleRepository.findAll().size() <= 0) {
			Role role = new Role(
					1,
					ERole.ROLE_ADMIN
			);
			Role role1 = new Role(
					2,
					ERole.ROLE_FUEL_STATION
			);
			Role role2 = new Role(
					3,
					ERole.ROLE_CUSTOMER
			);

			List<Role> roleList = new ArrayList<>();
			roleList.add(role);
			roleList.add(role1);
			roleList.add(role2);

			roleRepository.saveAll(roleList);
		}

		if (!userRepository.existsByUsername("malinga")) {
			Set<Role> roles = new HashSet<>();
			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
			User user = new User(
					1,
					"malinga",
					"mali@gmail.com",
					encoder.encode("12345"),
					roles
			);
			userRepository.save(user);
		}
		if (!userRepository.existsByUsername("fuelmoratuwa")) {
			Set<Role> roles = new HashSet<>();
			Role adminRole = roleRepository.findByName(ERole.ROLE_FUEL_STATION)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
			User user = new User(
					2,
					"fuelmoratuwa",
					"fuel@gmail.com",
					encoder.encode("12345"),
					roles
			);
			userRepository.save(user);
		}
		if (!userRepository.existsByUsername("nipuni")) {
			Set<Role> roles = new HashSet<>();
			Role adminRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
			User user = new User(
					3,
					"nipuni",
					"nipuni@gmail.com",
					encoder.encode("12345"),
					roles
			);
			userRepository.save(user);
		}
	}

}
