package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.ERole;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Role;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request.EmailDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request.LoginRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request.SignupRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.response.JwtResponse;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.response.MessageResponse;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.RoleRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.jwt.JwtUtils;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.EmailServiceImpl;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.UserDetailsImpl;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.UserDetailsServiceImpl;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.RandomPasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private RandomPasswordGenerator randomPasswordGenerator;
    @Value("${pwdreset.uri}")
    private String passwordResetUri;
    @Autowired
    private EmailServiceImpl emailService;


//    public static final String ACCOUNT_SID = System.getenv("ACaac2208b386d4c1c15f13c415399ff7b");
//    public static final String AUTH_TOKEN = System.getenv("169efa976db7a09cf637f228f15ffc87");

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    public ResponseEntity<CommonResponse> emailSendOTP(String email) {
        System.out.println("awa:" + email);
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
//            responseEntity = userDetailsService.sendEmail(email);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @PostMapping("/sendOTPSMS/{phoneNo}")
    public ResponseEntity<CommonResponse> smsSendOTP(@PathVariable("phoneNo") String phoneNo) {
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            StringSelection stringSelection = new StringSelection("Hai nipuni");
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);

//            responseEntity = userDetailsService.sendEmail(email);
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message message = Message.creator(
//                            new com.twilio.type.PhoneNumber("+94773043064"),//to
//                            new com.twilio.type.PhoneNumber("+15017122661"), // from
//                            "Hi there, How Are You")
//                    .create();
//
//            System.out.println(message.getSid());
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getNic(),signUpRequest.getPhoneNumber());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "fuelstationadmin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_FUEL_STATION)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "admin":
                        Role modRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        String randomPwd = randomPasswordGenerator.generatePassayPassword();
        EmailDTO mail = new EmailDTO();
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", signUpRequest.getUsername());
        model.put("lastName", "-"
                + signUpRequest.getNic());
        model.put("verificationCode", randomPwd);
        model.put("uri", passwordResetUri);

        mail.setTo(signUpRequest.getEmail());
        mail.setModel(model);
        emailService.sendEmailWithTemplate(mail);
        user.setPassword(encoder.encode(randomPwd));
        User userSaved = userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
