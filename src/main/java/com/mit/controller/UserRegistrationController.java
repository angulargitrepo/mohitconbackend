package com.mit.controller;

import com.mit.model.UserImage;
import com.mit.model.UserRegistrationModel;
import com.mit.repository.UserRegistrationRepository;
import com.mit.service.AmazonS3ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserRegistrationController {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private AmazonS3ClientServiceImpl amazonS3ClientService;

    @RequestMapping( method = RequestMethod.GET,value = "/getAllUserRegister", produces = {"application/JSON"})
   public @ResponseBody
    List<UserRegistrationModel> getAllUserRegister(){
        List<UserRegistrationModel> userRegistrationModelsList = userRegistrationRepository.findAll();
        for (UserRegistrationModel userRegistrationModel: userRegistrationModelsList) {
            byte[] userImageFromS3 = this.amazonS3ClientService.getFileFromS3Bucket(userRegistrationModel.getFirstName());
            if(userImageFromS3!= null)
                userRegistrationModel.setUserImage(new UserImage(userRegistrationModel.getFirstName(),"image",userImageFromS3));
        }
        log.info(" Logged getAllUserRegister()");
        return userRegistrationModelsList;
    }

    @RequestMapping(value = "/saveUserRegistration", method = RequestMethod.POST)
    public ResponseEntity<?> saveUserRegistration(@RequestBody UserRegistrationModel userRegistrationModel) {
        saveOrUpdate(userRegistrationModel);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/deleteUserRegistration", method = RequestMethod.POST)
    public ResponseEntity<?> deleteUserRegistration(@RequestBody UserRegistrationModel userRegistrationModel) {
        System.out.println(userRegistrationModel.toString());
        this.amazonS3ClientService.deleteFileFromS3Bucket(userRegistrationModel.getFirstName());
        // other statement
        userRegistrationRepository.delete(userRegistrationModel);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/updateUserRegistration", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserRegistration(@RequestBody UserRegistrationModel userRegistrationModel) {
        saveOrUpdate(userRegistrationModel);
        return ResponseEntity.ok().build();
    }

    public void saveOrUpdate(UserRegistrationModel userRegistrationModel){
        System.out.println("Calling server side save "+userRegistrationModel.toString());
        // other statement
        this.userRegistrationRepository.save(userRegistrationModel);
        log.debug("User saved/update successfully..");
        if(userRegistrationModel.getUserImage()!=null && userRegistrationModel.getUserImage().getImage()!=null){
            this.amazonS3ClientService.uploadFileToS3Bucket(userRegistrationModel.getFirstName(), userRegistrationModel.getUserImage().getImage());
        }
    }
}
