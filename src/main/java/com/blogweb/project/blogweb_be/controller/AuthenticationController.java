package com.blogweb.project.blogweb_be.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogweb.project.blogweb_be.dto.request.AuthenticationRequest;
import com.blogweb.project.blogweb_be.dto.request.IntrospectRequest;
import com.blogweb.project.blogweb_be.dto.request.InvalidTokenRequest;
import com.blogweb.project.blogweb_be.dto.respone.ApiResponse;
import com.blogweb.project.blogweb_be.dto.respone.AuthenticationResponse;
import com.blogweb.project.blogweb_be.dto.respone.IntrospectRespone;
import com.blogweb.project.blogweb_be.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody InvalidTokenRequest request) throws JOSEException, ParseException {
        authenticationService.logout(request);

        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectRespone> introspect(@RequestBody IntrospectRequest request) {
        return ApiResponse.<IntrospectRespone>builder()
                .result(authenticationService.introspect(request))
                .build();
    }
}
