package com.mykhailotiutiun.orderservice.model;

import io.katharsis.resource.annotations.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonApiResource(type = "orders")
public class Order {

    @Id
    @JsonApiId
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String firstName;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String secondName;
    @Size(max = 100)
    @Column(nullable = false)
    private String phoneNumber;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Email
    @Column(nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false)
    private Long userId;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> productIds = new java.util.ArrayList<>();
}
