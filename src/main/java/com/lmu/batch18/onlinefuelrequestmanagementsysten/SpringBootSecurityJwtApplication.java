package com.lmu.batch18.onlinefuelrequestmanagementsysten;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.*;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class SpringBootSecurityJwtApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private EligibleQuotaRepository eligibleQuotaRepository;

	@Autowired
	private FuelPriceRepo fuelPriceRepo;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private FuelStationRepository fuelStationRepository;

	@Autowired
	private FuelRequestByFuelStationRepository fuelRequestByFuelStationRepository;

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
		if(eligibleQuotaRepository.count()<=0){
			List<EligibleQuota>eligibleQuotaList = new ArrayList<>();
			EligibleQuota eligibleQuota = new EligibleQuota(
					1,
					"PETROL",
					"CAR",
					20
			);
			eligibleQuotaList.add(eligibleQuota);

			EligibleQuota eligibleQuota1 = new EligibleQuota(
					2,
					"PETROL",
					"VAN",
					20
			);
			eligibleQuotaList.add(eligibleQuota1);
			EligibleQuota eligibleQuota2 = new EligibleQuota(
					3,
					"PETROL",
					"MOTOR-BIKE",
					4
			);
			eligibleQuotaList.add(eligibleQuota2);

			EligibleQuota eligibleQuota3 = new EligibleQuota(
					4,
					"PETROL",
					"THREE-WHEELER",
					4
			);
			eligibleQuotaList.add(eligibleQuota3);

			EligibleQuota eligibleQuota4 = new EligibleQuota(
					5,
					"PETROL",
					"CUTTING-GRASS-MACHINE",
					4
			);
			eligibleQuotaList.add(eligibleQuota4);

			EligibleQuota eligibleQuota5 = new EligibleQuota(
					6,
					"DIESEL",
					"CAR",
					20
			);
			eligibleQuotaList.add(eligibleQuota5);

			EligibleQuota eligibleQuota6 = new EligibleQuota(
					7,
					"DIESEL",
					"VAN",
					25
			);
			eligibleQuotaList.add(eligibleQuota6);

			EligibleQuota eligibleQuota7 = new EligibleQuota(
					8,
					"DIESEL",
					"BUS",
					100
			);
			eligibleQuotaList.add(eligibleQuota7);

			eligibleQuotaRepository.saveAll(eligibleQuotaList);
		}

		if(fuelPriceRepo.count()<=0){
			List<FuelPrice> fuelPriceList = new ArrayList<>();
			FuelPrice fuelPrice = new FuelPrice(
					1,
					"PETROL",
					400.00
			);
			fuelPriceList.add(fuelPrice);
			FuelPrice fuelPrice1 = new FuelPrice(
					2,
					"DIESEL",
					325.00
			);
			fuelPriceList.add(fuelPrice1);
			fuelPriceRepo.saveAll(fuelPriceList);
		}

//		if(customerRepository.count()<=0){
//
//			Customer customer = new Customer(
//					1,
//					"Gotabaya",
//					"123134131",
//					new Date(),
//					true,
//					"Colombo",
//					userRepository.getReferenceById(2),
//					null,
//					null
//			);
//			customerRepository.save(customer);
//		}

		if(vehicleRepository.count()<=0){
			Vehicle vehicle = new Vehicle(
					1,
					"AB1223",
					"1232343234",
					"CAR",
					"PETROL",
					"Gotabaya",
					"2",
					new Date(),
					"Minod",
					new Date(),
					null,
					null

			);
			vehicleRepository.save(vehicle);
		}

		if(fuelStationRepository.count()<=0){
			FuelStation fuelStation = new FuelStation(
					1,
					"Colombo Shed",
					"Colombo",
					true,
					userRepository.getReferenceById(2),
					null,
					null
			);

			fuelStationRepository.save(fuelStation);
		}

		if(fuelRequestByFuelStationRepository.count()<=0){
			FuelStation fuelStation = fuelStationRepository.getReferenceById(13);
			FuelRequestByFuelStation fuelRequestByFuelStation = new FuelRequestByFuelStation(
					1,
					2.00,
					fuelStation
			);
			fuelRequestByFuelStationRepository.save(fuelRequestByFuelStation);

		}
	}




}
