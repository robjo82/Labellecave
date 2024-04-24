package com.labellecave.product.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private float alcoholDegree;

    @NotBlank
    private int volume;

    @NotBlank
    private float price;

    @NotBlank
    private int stock;
}
