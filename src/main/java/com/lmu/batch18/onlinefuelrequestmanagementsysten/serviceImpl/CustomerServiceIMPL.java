package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.CustomerRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.CustomerService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonConst;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceIMPL implements CustomerService {

    Map<String, Object> response = new HashMap<>();
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<CommonResponse> saveCustomer(CustomerDTO customerDTO) {
        CommonResponse commonResponse = new CommonResponse();
        User user = userRepository.getReferenceById(customerDTO.getUserId());
        Customer customerExist = customerRepository.findByUserEquals(user);
        response.clear();
        if (customerExist != null) {
            commonResponse.setErrorMessages(Collections.singletonList("User Id Already Exists."));
            commonResponse.setStatus(CommonConst.CONFLICT);
            return new ResponseEntity<>(commonResponse, HttpStatus.CONFLICT);
        } else {
            customerDTO.setUser(user);
            Customer customer = modelMapper.map(customerDTO, Customer.class);
            //customer.setUser(user);
            Customer customerSaved = customerRepository.save(customer);
            response.put("Vehicle", customerSaved);
            commonResponse.setPayload(Arrays.asList(response));
            commonResponse.setStatus(CommonConst.CREATED);
            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        }
    }

    @Override
    public CustomerDTO getCustomerByUserId(int userId) {
        User u = new User();
        u.setId(userId);
        Customer customer = customerRepository.findByUserEquals(u);
        CustomerDTO c = modelMapper.map(customer, CustomerDTO.class);
        return c;

    }

    @Override
    public List<CustomerDTO> getALlCustomers() {

        List<Customer> customer = customerRepository.findAll();
        List<CustomerDTO> c = modelMapper.
                map(customer, new TypeToken<List<CustomerDTO>>() {
                }.getType());
        return c;
    }

    @Override
    public ResponseEntity<CommonResponse> updateCustomer(int id, CustomerDTO customerDTO) {
        CommonResponse commonResponse = new CommonResponse();
        Customer customer = customerRepository.findById(id).get();
        if (customer==null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found customer"));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        Customer customer1 = customerRepository.save(new Customer(
                customer.getId(),
                customer.getName(),
                customer.getNic(),
                customer.getBirthdate(),
                customerDTO.getAddress(),
                customer.getUser()
        ));
        commonResponse.setPayload(Collections.singletonList(customer1));
        commonResponse.setStatus(1);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
