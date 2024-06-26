package com.mykhailotiutiun.userservice.model;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonApiResource(type = "users")
public class User {

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
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Email
    @Column(nullable = false)
    private String email;
    @Size(max = 255)
    private String phoneNumber;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String password;

}
