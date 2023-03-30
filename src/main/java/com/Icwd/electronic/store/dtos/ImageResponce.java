package com.Icwd.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponce {
    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;
}
