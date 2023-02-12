package com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services;

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

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public ResponseEntity<CommonResponse> sendEmail(String email) {
    System.out.println("USer service impl");
    CommonResponse commonResponse = new CommonResponse();
    String randomPwd = randomPasswordGenerator.generatePassayPassword();
    System.out.println(randomPwd);

    //send an email
        /*emailService.sendEmail(user.getEmail(),"Verification Code",
                "This is system generated verification code: "+randomPwd);*/

    //send an email with template
    EmailDTO mail = new EmailDTO();
    Map<String, Object> model = new HashMap<>();
    model.put("firstName", "Yuneth");
    model.put("lastName", "Anjana");
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
}
