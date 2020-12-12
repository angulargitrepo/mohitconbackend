package com.mit.controller;

import com.mit.model.LoginUserModel;
import com.mit.repository.UserRegistrationRepository;
import com.mit.service.AmazonS3ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/login")
@ApiIgnore
@Slf4j
public class LoginController {

    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private AmazonS3ClientServiceImpl amazonS3ClientService;

    @RequestMapping(value = "/validateLoginUser", method = RequestMethod.GET)
    public LoginUserModel validateLoginUser(@RequestParam String userName,@RequestParam String password) {
        System.out.println(userName.toString());
        // other statement
        Optional<UserRegistrationRepository> userRegistration = userRegistrationRepository.validateLoginUser(userName,password);
       /* HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);*/
        LoginUserModel loginUserModel1=new LoginUserModel();
        byte[] userImageFromS3 = this.amazonS3ClientService.getFileFromS3Bucket(userName);
        if(userImageFromS3!= null) {
            loginUserModel1.setImage(userImageFromS3);
        }
        loginUserModel1.setUserName(userName);
        loginUserModel1.setStatus(userRegistration.isPresent() ? "success": "Unauthorized");
        log.info("Login ::"+loginUserModel1.getStatus());
        return loginUserModel1;
    }
}
