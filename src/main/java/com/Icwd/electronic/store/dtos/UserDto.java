package com.Icwd.electronic.store.dtos;

import com.Icwd.electronic.store.validate.ImageNameValid;
import lombok.*;
import org.w3c.dom.ranges.RangeException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min=3, max=15,message="Invalid Name!!")
    private String name;
    //@Email(message="Invalid User Email !!")
    @NotBlank( message = "Email is required!!")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid email!!")
    private String email;
    @NotBlank(message = "Please enter your corret password")
    private String password;
    @Size(min=3, max= 6, message = "invalid gender")
    private String gender;
  @NotBlank(message = "Write something in about yourself!!")
    private String about;
    @ImageNameValid
    private String imageName;

    //@pattern
    //Custom validator
}
