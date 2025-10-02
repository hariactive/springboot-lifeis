package com.Lifeisgood.products;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

//    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Name cannot be blank")
    @Size(min = 3, max = 20, message = "min 3 to 20 characters long size is allowed")
    private String name;

    @NotNull(message = "price is required")
    @PositiveOrZero(message = "price must be >= 0")
    private Double price;

    private String description;

    private String imageUrl;

    private Integer stockQuantity;

    private String category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
