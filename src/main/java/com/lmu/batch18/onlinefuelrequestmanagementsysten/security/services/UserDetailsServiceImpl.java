package com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request.EmailDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RandomPasswordGenerator randomPasswordGenerator;
    @Value("${pwdreset.uri}")
    private String passwordResetUri;
    @Autowired
    private EmailServiceImpl emailService;

    private final String mailSubject = "Fule Station verification code";
    private final String mailSubjectNewSchedule = "New Schedule For Token";

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public ResponseEntity<CommonResponse> sendEmail(String email, User user) {

        CommonResponse commonResponse = new CommonResponse();
        String randomPwd = randomPasswordGenerator.generatePassayPassword();


        //send an email
        /*emailService.sendEmail(user.getEmail(),"Verification Code",
                "This is system generated verification code: "+randomPwd);*/

        //send an email with template
        EmailDTO mail = new EmailDTO();
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", "Iresha");
        model.put("lastName", "Dolawatha");
        model.put("verificationCode", randomPwd);
        model.put("uri", passwordResetUri);

        mail.setTo(email);
        mail.setModel(model);
        emailService.sendEmailWithTemplate(mail);


//    user.setPassword(bcryptEncoder.encode(randomPwd));
//    userRepository.save(user);

        commonResponse.setStatus(1);
        commonResponse.setPayload(Collections.singletonList("Email sent successfully.."));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    public String sendEmailOTP(FuelStation fuelStation) {

        CommonResponse commonResponse = new CommonResponse();
        String randomPwd = randomPasswordGenerator.generatePassayPassword();


        //send an email
        /*emailService.sendEmail(user.getEmail(),"Verification Code",
                "This is system generated verification code: "+randomPwd);*/

        //send an email with template
        EmailDTO mail = new EmailDTO();
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", fuelStation.getManagerFirstName());
        model.put("lastName", fuelStation.getManagerLastName());
        model.put("verificationCode", randomPwd);
        model.put("uri", passwordResetUri);

        mail.setTo(fuelStation.getManagerEmail());
        mail.setModel(model);
        emailService.sendEmailWithTemplate(mail);

//    User signupRequest=new User() ;
//    signupRequest.setUsername(fuelStation.getManagerFirstName());
//    signupRequest.setEmail(fuelStation.getManagerEmail());
////    signupRequest.setRoles(Collections.singleton("fuelstationadmin"));
//    signupRequest.setPassword(randomPwd);
//    userRepository.save(signupRequest);


//    user.setPassword(bcryptEncoder.encode(randomPwd));
//    userRepository.save(user);

        commonResponse.setStatus(1);
        commonResponse.setPayload(Collections.singletonList(randomPwd));
        return randomPwd;
//    return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
    }

    public String sendEmailWithNewSchedule(NewSchedule newSchedule, User user) {
        CommonResponse commonResponse = new CommonResponse();
        //      String randomPwd = randomPasswordGenerator.generatePassayPassword();
        EmailDTO mail = new EmailDTO();
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", user.getUsername());
        System.out.println("new time:" + newSchedule.getAvailableTime());
        model.put("scheduleDate", newSchedule.getAvailableDate());
        model.put("scheduleTime", newSchedule.getAvailableTime());
        mail.setTo(user.getEmail());
        mail.setModel(model);
        emailService.sendEmailWithTemplateForNewSchedule(mail, mailSubjectNewSchedule);
        return "SUCCESSFULL";
    }
}
