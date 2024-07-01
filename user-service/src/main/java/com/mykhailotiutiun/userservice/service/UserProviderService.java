package com.mykhailotiutiun.userservice.service;

import com.mykhailotiutiun.userservice.grpc.*;
import com.mykhailotiutiun.userservice.model.User;
import com.mykhailotiutiun.userservice.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@GrpcService
public class UserProviderService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;

    public UserProviderService(UserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    @Transactional
    public void registerUser(final RegisterUserRequest request, final StreamObserver<RegisterUserResponse> responseStreamObserver) {
        User user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .phoneNumber(request.getPhoneNumber())
                .role(com.mykhailotiutiun.userservice.model.Role.USER)
                .enabled(false)
                .build());
        responseStreamObserver.onNext(
                RegisterUserResponse.newBuilder()
                        .setId(user.getId())
                        .setEmail(user.getEmail())
                        .setFirstName(user.getFirstName())
                        .setSecondName(user.getSecondName())
                        .setPhoneNumber(user.getPhoneNumber())
                        .setEnabled(user.getEnabled())
                        .build()
        );
        emailVerificationService.beginVerification(user);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void loginUser(final LoginUserRequest request, final StreamObserver<LoginUserResponse> responseStreamObserver) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(EntityNotFoundException::new);
        responseStreamObserver.onNext(
                LoginUserResponse.newBuilder()
                        .setId(user.getId())
                        .setEmail(user.getEmail())
                        .setPhoneNumber(user.getPhoneNumber())
                        .setPassword(user.getPassword())
                        .setRole(Role.forNumber(user.getRole().ordinal()))
                        .setEnabled(user.getEnabled())
                        .build()
        );
        responseStreamObserver.onCompleted();
    }

    @Override
    public void confirmEmail(final ConfirmEmailRequest request, final StreamObserver<ConfirmEmailResponse> responseStreamObserver){
        emailVerificationService.verify(request.getToken());
        responseStreamObserver.onNext(ConfirmEmailResponse.newBuilder().build());
        responseStreamObserver.onCompleted();
    }
}
