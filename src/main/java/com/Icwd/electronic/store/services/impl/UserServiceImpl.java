package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.helper.Helper;
import com.Icwd.electronic.store.repositories.UserRepository;
import com.Icwd.electronic.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.path}")
    private String imagePath;
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Initiating the dao call for create the user data ");
        //generate unique Id
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //dto -> entity
        User user = dtoToEntity(userDto);
        User savedUser= userRepository.save(user);

        //entity -> dto
        UserDto newDto = entityToDto(savedUser);
        log.info("Complete the dao call for create the user data :");
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Initiating the dao call for update the user data with id{}:",userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given id"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        log.info("Complete the dao call for update the user data with id{}:",userId);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId)  {
        log.info("Initiating the dao call for delete the user data with id{}:",userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given id"));
       //delete user profile image
        //images/user/abc.png
        String fullPath = imagePath+user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex) {
            log.info("User image not found in folder");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        // delete user
        userRepository.delete(user);
        log.info("Complete the dao call for delete the user data with id{}:",userId);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call to get all user from data ");

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        //page number default start with 0
        Pageable pageable = PageRequest.of( pageNumber,pageSize,sort);
       Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        log.info("Complete the dao call to get all user from data");
        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        log.info("Initiating the dao call to get user from data with id{}:",userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given id"));
        log.info("Complete the dao call to get user from data with id{}:",userId);
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Initiating the dao call to get user from data with email{}:",email);
      User user  = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException(" User not found with given email id and password !!"));
        log.info("Complete the dao call to get user from data with email{}:",email);
        return entityToDto(user);
    }

    @Override
    public List<UserDto> serchUser(String Keyword) {
        log.info("Initiating the dao call to search user from data with Keyword{}:",Keyword);
       List<User> users = userRepository.findByNameContaining(Keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("Complete the dao call  to search user from data with Keyword{}:",Keyword);
        return dtoList;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return userRepository.findByEmail(email);}

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
        return mapper.map(savedUser, UserDto.class);
    }


    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();

        return mapper.map(userDto,User.class);
    }
}
