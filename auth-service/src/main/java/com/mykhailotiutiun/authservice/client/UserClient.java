package com.mykhailotiutiun.authservice.client;

import com.mykhailotiutiun.authservice.dto.RegisterRequest;
import com.mykhailotiutiun.authservice.dto.RegisterResponse;
import com.mykhailotiutiun.authservice.grpc.*;
import com.mykhailotiutiun.authservice.model.Role;
import com.mykhailotiutiun.authservice.model.CustomUserDetails;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableEurekaClient
public class UserClient {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    public CustomUserDetails findUser(String email) {
        final LoginUserResponse response = userServiceStub.loginUser(LoginUserRequest.newBuilder().setEmail(email).build());
        return CustomUserDetails.builder()
                .id(response.getId())
                .email(response.getEmail())
                .password(response.getPassword())
                .role(Role.valueOf(response.getRole().name()))
                .build();
    }

    public RegisterResponse register(RegisterRequest request) {
        final RegisterUserResponse response = userServiceStub.registerUser(RegisterUserRequest.newBuilder()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setFirstName(request.getFirstName())
                .setSecondName(request.getSecondName())
                .setPhoneNumber(request.getPhoneNumber())
                .build());
        return RegisterResponse.builder()
                .id(response.getId())
                .email(response.getEmail())
                .firstName(response.getFirstName())
                .secondName(response.getSecondName())
                .phoneNumber(response.getPhoneNumber())
                .build();
    }
}
