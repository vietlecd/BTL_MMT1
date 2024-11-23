package com.project.btl_mmt1.controller;

import com.project.btl_mmt1.components.CookieUtil;
import com.project.btl_mmt1.dto.LoginDTO;
import com.project.btl_mmt1.dto.SignupDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import com.project.btl_mmt1.service.IAuthService;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@AllArgsConstructor
public class AuthController {

    private IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupDTO userDTO,
                                        BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password not match");
            }
            authService.createUser(userDTO);//return ResponseEntity.ok("Register successfully");
            return ResponseEntity.ok("Registered successful");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); //rule 5
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login (
            @Valid @RequestBody LoginDTO userLoginDTO,
            HttpServletResponse response) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = authService.login(userLoginDTO);

            // Trả về token trong response
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response) {
//
//        // Xoa Cokie
//        //CookieUtil.deleteTokenCookie(response);
//
//
//        return ResponseEntity.ok("Logged out successfully");
//    }

}
