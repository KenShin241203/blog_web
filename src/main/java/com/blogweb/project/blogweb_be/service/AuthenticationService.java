package com.blogweb.project.blogweb_be.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.AuthenticationRequest;
import com.blogweb.project.blogweb_be.dto.request.IntrospectRequest;
import com.blogweb.project.blogweb_be.dto.request.InvalidTokenRequest;
import com.blogweb.project.blogweb_be.dto.respone.AuthenticationResponse;
import com.blogweb.project.blogweb_be.dto.respone.IntrospectRespone;
import com.blogweb.project.blogweb_be.entity.InvalidToken;
import com.blogweb.project.blogweb_be.exception.AppException;
import com.blogweb.project.blogweb_be.exception.ErrorCode;
import com.blogweb.project.blogweb_be.repository.InvalidRepository;
import com.blogweb.project.blogweb_be.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    InvalidRepository invalidRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .jwt_token(token)
                .build();
    }

    public IntrospectRespone introspect(IntrospectRequest request) {
        boolean isActive = false;
        try {
            verifyToken(request.getToken());
            isActive = true;
        } catch (Exception e) {
            isActive = false;
        }
        return IntrospectRespone.builder()
                .active(isActive)
                .build();
    }

    public void logout(InvalidTokenRequest request) throws JOSEException, ParseException {
        try {
            var signToken = verifyToken(request.getToken());
            String jti = signToken.getJWTClaimsSet().getJWTID();
            Date expTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidToken invalidToken = InvalidToken.builder()
                    .id(jti)
                    .expireTime(expTime)
                    .build();
            invalidRepository.save(invalidToken);
        } catch (AppException e) {
            log.info("Token already expired!!");
        }
    }

    public String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("com.blogweb")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(verifier);

        if (!verified || signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }
}
