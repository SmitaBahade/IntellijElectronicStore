package com.Icwd.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;
    @NotBlank
    @Min(value=4, message="title must be of minimum 4 charectors !!")
    private String title;

    @NotBlank(message="description required !!")
    private String description;
    private String coverImage;
}
