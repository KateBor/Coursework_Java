package spring_main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring_main.entity.User;
import spring_main.repository.UserRepository;
import spring_main.request.AuthRequest;
import spring_main.security.jwt.JwtTokenProvider;
import java.util.Optional;


@RestController
@RequestMapping("/company/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder pwdEncoder;


    @PostMapping(value = "/signIn", consumes = "application/json")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        try{
            String name = request.getUserName();
            String password = request.getPassword();
            Optional<User> user = userRepository.findUserByUserName(name);
            boolean passwordMatch = false;
            if (user.isPresent()) {
                User us = user.get();
                passwordMatch = pwdEncoder.matches(password, us.getPassword());
            }

            if (!passwordMatch)
                throw new BadCredentialsException("Invalid username or password");


            String token = jwtTokenProvider.createToken(
                    name,
                    user
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles()
            );

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
