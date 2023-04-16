package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    //Create user
    @Autowired
    private ModelMapper mapper;

    User user;
    UserDto userDto;
    User user1;
    User user2;
    User user3;
    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Smita")
                .email("smita@gmail.com")
                .about("This is testing of ceate user")
                .gender("Female")
                .imageName("abc.png")
                .password("lcwd")
                .build();

       userDto = UserDto.builder()
                .name("Smita Bahade")
                .about("This is updated user details")
                .gender("Female")
                .imageName("xyz.png")
                .build();

        user1 = User.builder()
                .name("Krutika")
                .email("Krutika@gmail.com")
                .about("This is testing of getAllUsers")
                .gender("Female")
                .imageName("abc.png")
                .password("lcwd")
                .build();

         user2 = User.builder()
                .name("Namita")
                .email("Namita@gmail.com")
                .about("This is testing of getAllUsers")
                .gender("Female")
                .imageName("abc.png")
                .password("lcwd")
                .build();

         user3 = User.builder()
                .name("Rashmi Tode")
                .email("rashmi@gmail.com")
                .about("This is testing of getAllUsers")
                .gender("Female")
                .imageName("abc.png")
                .password("lcwd")
                .build();
//        User user1 = User.builder()
//                .name("Pranu Pande")
//                .email("pranu@gmail.com")
//                .about("This is testing of getAllUsers")
//                .gender("Female")
//                .imageName("abc.png")
//                .password("lcwd")
//                .build();
//        User user2 = User.builder()
//                .name("Sonali Thakur")
//                .email("sonali@gmail.com")
//                .about("This is testing of getAllUsers")
//                .gender("Female")
//                .imageName("abc.png")
//                .password("lcwd")
//                .build();
    }

    @Test
    public void createUserTest() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Smita", user1.getName());

    }

    //Update user test
    @Test
    public void updateUserTest() {
        String userId = "ydvygdviydv";

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);
        //   UserDto updatedUser = mapper.map(user, UserDto.class);
        System.out.println(updatedUser.getName());
        System.out.println(updatedUser.getImageName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(), updatedUser.getName(), "name is not validate");
    }

    //delete use test case
    @Test
    public void deleteUserTest() throws IOException {
        String userid = "userIdabc";

        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
        userService.deleteUser(userid);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }
    //getAllUsers

    @Test
    public void getAllUsersTest() {

        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3, allUser.getContent().size());
    }

    //getUserById
    @Test
    public void getUserByIdTest() {

        String userId = "userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        //actual call of service method

        UserDto userDto = userService.getUserById(userId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "Nmae not match...");
    }

    //getUserByEmail
    @Test
    public void getUserByEmailTest() {
        String email = "smita@gmail.com";
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail(email);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(), userDto.getEmail(), "email not matched...!!");
    }

    //searchUser
    @Test
    public void searchUserTest() {


        String Keywords = "Pranu Pande";
        Mockito.when(userRepository.findByNameContaining(Keywords)).thenReturn(Arrays.asList(user, user1, user2, user3));
        List<UserDto> userDtos = userService.serchUser(Keywords);
        Assertions.assertEquals(4, userDtos.size(), "Size not matched...!!");
    }

    //findUserByEmail
//    @Test
//    public void findUserByEmailOptionalTest() {
//        String email = "smita@gmail.com";
//    Mockito.when(UserRepository.findByEmail(email)).thenReturn(Optional.of(user));
//        Optional<User> userByEmailOptional = userService.findUserByEmailOptional(email);
//        Assertions.assertTrue(userByEmailOptional.isPresent());
//
//        User user1= userByEmailOptional.get();
//        Assertions.assertEquals(user.getEmail(),user1.getEmail(),"Email not matched...!!");

    }
