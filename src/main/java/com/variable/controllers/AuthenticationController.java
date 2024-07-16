package com.variable.controllers;

import com.variable.entities.User;
import com.variable.dtos.LoginUserDto;
import com.variable.dtos.RegisterUserDto;
import com.variable.repositories.UserRepository;
import com.variable.responses.LoginResponse;
import com.variable.services.AuthenticationService;
import com.variable.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        return "signup";
    }


//ResponseEntity<LoginResponse>
    @PostMapping("/login")
    public void authenticate(@RequestBody LoginUserDto loginUserDto, HttpServletResponse response) throws IOException {
        try {

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), authenticatedUser);

        Cookie jwtCookie = new Cookie("JWT", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Use true in production
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) jwtService.getExpirationTime());

        // Add the cookie to the response
        response.addCookie(jwtCookie);

        // Redirect to the web application
        response.sendRedirect("/horay");
    } catch (AuthenticationException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
}
