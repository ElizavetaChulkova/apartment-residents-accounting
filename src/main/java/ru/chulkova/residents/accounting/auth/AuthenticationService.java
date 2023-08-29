package ru.chulkova.residents.accounting.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chulkova.residents.accounting.model.User;
import ru.chulkova.residents.accounting.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        log.info("register service");
        var user = User.builder()
                .name(request.getName())
                .age(request.getAge())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        log.info("user is saved to db");
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("authentication service");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            log.info("user is authenticated");
        } catch (AuthenticationException e) {
            log.error("access denied");
            throw new AccessDeniedException("Access denied");
        }
        var user = repository.findByName(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("user is find in db");
        var jwtToken = jwtService.generateToken(user);
        log.info("token is generated");
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }
}