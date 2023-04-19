package com.Icwd.electronic.store.controllers;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.awt.*;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc; //request karne ke liye movkmvc ka obj use karo

    private User user;
    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Smita")
                .email("smita@gmail.com")
                .about("This is testing of ceate user")
                .gender("Female")
                .imageName("abc.png")
                .password("lcwd")
                .build();
    }
    @Test
    public void createUserTest() throws Exception {
        //users+post+user data as json
        //data as json + status dreated
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //actual request for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateUserTest() throws Exception { // IT IS SECURED API
        //  /users/{userId}+put+user data as json
        String userId="123";
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/"+ userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }
//    @Test
//    public void getAllUsersTest(){
//        UserDto.builder().name(("Smita").email("smita@gmail.com").password("smita").about("Testing").build());
//
//        PageableResponse<UserDto> pageableResponse= new  PageableResponse<>();
//        pageableResponse.setContent(Arrays.asList()
//
//        );
//        pageableResponse.setLastPage(false);
//        pageableResponse.setPageSize(10);
//        pageableResponse.setPageNumber(100);
//        pageableResponse.setTotalElements(1000);
//
//        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.any(),Mockito.anyString())).thenReturn(pageableResponse);
//    }
    private String convertObjectToJsonString(Object user) {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
