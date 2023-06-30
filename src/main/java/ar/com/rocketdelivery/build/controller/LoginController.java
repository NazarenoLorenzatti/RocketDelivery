package ar.com.rocketdelivery.build.controller;

import ar.com.rocketdelivery.build.domain.Json.JwtResponse;
import ar.com.rocketdelivery.build.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public JwtResponse login(@RequestParam String username, @RequestParam String password) {
        return this.loginService.login(username, password);
    }

}
