package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    //create
    UserDto createUser (UserDto userDto);


    //update
    UserDto updateUser (UserDto userDto, String userId);


    //delete
    void deleteUser(String userId) throws IOException;

    //get all users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single user by id
    UserDto getUserById (String userId);

    // get single user by email
    UserDto getUserByEmail (String email);

    //serch user
    List<UserDto> serchUser(String Keyword);
}
