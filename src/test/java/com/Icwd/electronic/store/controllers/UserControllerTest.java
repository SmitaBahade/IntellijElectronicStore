package com.Icwd.electronic.store.controllers;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.awt.*;

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
                .contentType(PageAttributes.MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    private String convertObjectToJsonString(Object user) {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
