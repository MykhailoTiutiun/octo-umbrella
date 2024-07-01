package com.mykhailotiutiun.authservice.controller;

import com.mykhailotiutiun.authservice.client.UserClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class ConfirmRegisterController {

    private final UserClient userClient;

    public ConfirmRegisterController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/confirm-registration")
    public String confirmRegister(@RequestParam("token") String token){
        userClient.confirmRegister(token);
        return "redirect://localhost:3000/login";
    }
}
