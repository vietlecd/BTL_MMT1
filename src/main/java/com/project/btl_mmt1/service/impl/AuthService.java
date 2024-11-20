package com.project.btl_mmt1.service.impl;

import com.project.btl_mmt1.dto.LoginDTO;
import com.project.btl_mmt1.dto.SignupDTO;
import com.project.btl_mmt1.repositories.UserRepository;
import com.project.btl_mmt1.service.IAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.project.btl_mmt1.components.JwtTokenUtil;
import com.project.btl_mmt1.customexceptions.DataNotFoundException;
import com.project.btl_mmt1.customexceptions.PermissionDenyException;
import com.project.btl_mmt1.models.User;
import com.project.btl_mmt1.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    @Override
    public User createUser(SignupDTO userDTO) throws Exception {
        //register user
        String username = userDTO.getUsername();

        if(userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("username already exists");
        }

        //convert from userDTO => userEntity
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();


        //Decoed kí tự password
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }


    @Override
    public String login(LoginDTO loginDTO) throws Exception {
            Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
            if(optionalUser.isEmpty()) {
                throw new DataNotFoundException("Invalid username / password");
            }
            //return optionalUser.get();//muốn trả JWT token ?
            User existingUser = optionalUser.get();
            //check password
            if(!passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong username or password");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()
            );

            //authenticate with Java Spring security
            authenticationManager.authenticate(authenticationToken);

            return jwtTokenUtil.generateToken(existingUser);
    }
}
