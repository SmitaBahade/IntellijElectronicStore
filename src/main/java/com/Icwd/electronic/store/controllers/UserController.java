package com.Icwd.electronic.store.controllers;

import com.Icwd.electronic.store.dtos.ApiResponseMessage;
import com.Icwd.electronic.store.dtos.ImageResponce;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.services.FileService;
import com.Icwd.electronic.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("In UserController class createUser method start");
        UserDto userDto1 = userService.createUser(userDto);
        log.info("In UserController class createUser method ended");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser( @PathVariable("userId")String userId,@Valid @RequestBody UserDto userDto) {
        log.info("In UserController class updateUser method start with id:",userId);
        UserDto updatedUserDto= userService.updateUser(userDto,userId);
        log.info("In UserController class updateUser method ended with id:",userId);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage>deleteUser(@PathVariable String userId) throws IOException {
        log.info("In UserController class deleteUser method start with id:",userId);
        userService.deleteUser(userId);
        ApiResponseMessage message  = ApiResponseMessage.builder().message("User is deleted successfully !!").success(true).status(HttpStatus.OK).build();
        log.info("In UserController class deleteUser method ended with id:",userId);
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam( value = "pageNumber", defaultValue="0",required =false)int pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue="10",required =false)int pageSize,
                                                                 @RequestParam( value = "sortBy", defaultValue="name",required =false)String sortBy,
                                                                 @RequestParam(value = "sortDir", defaultValue="asc",required =false)String sortDir){
        log.info("In UserController class getAllUsers method start");;
        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
        log.info("In UserController class getAllUsers method ended");
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }

    //get Single
   @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
       log.info("In UserController class getUser method start with id:",userId);
       UserDto userById = userService.getUserById(userId);
       log.info("In UserController class getUser method ended with id:",userId);
       return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        log.info("In UserController class getUserByEmail method start with :",email);
        UserDto userByEmail = userService.getUserByEmail(email);
        log.info("In UserController class getUserByEmail method ended with :",email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }

    //Search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> serchUser(@PathVariable String keywords){
        log.info("In UserController class serchUser method start with:",keywords);
        List<UserDto> userDtos = userService.serchUser(keywords);
        log.info("In UserController class serchUser method ended with:",keywords);
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    //upload user Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponce> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        log.info("In UserController class uploadUserImage method start with id:",userId);
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService  .getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        ImageResponce imageResponce = ImageResponce.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        log.info("In UserController class uploadUserImage method ended with id:",userId);
        return new ResponseEntity<>(imageResponce,HttpStatus.CREATED);
    }



    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("In UserController class serveUserImage method start with id:",userId);
        UserDto user = userService.getUserById(userId);
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("In UserController class serveUserImage method ended with id:",userId);

    }

}
