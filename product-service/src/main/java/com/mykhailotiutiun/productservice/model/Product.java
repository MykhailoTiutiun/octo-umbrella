package com.mykhailotiutiun.productservice.model;


import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonApiResource(type = "products")
public class Product {

    @Id
    @JsonApiId
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String title;
    @NotNull
    @NotBlank
    @Size(max = 5000)
    @Column(nullable = false, length = 5000)
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images = new java.util.ArrayList<>();
    @NotNull
    @Column(nullable = false)
    private Float price;
}

