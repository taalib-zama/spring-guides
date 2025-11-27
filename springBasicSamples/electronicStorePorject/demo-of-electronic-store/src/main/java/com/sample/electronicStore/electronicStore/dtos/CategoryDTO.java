package com.sample.electronicStore.electronicStore.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "Category title must be at least 4 characters long")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank
    private String coverImage;

}
