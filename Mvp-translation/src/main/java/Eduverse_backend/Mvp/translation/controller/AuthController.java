package Eduverse_backend.Mvp.translation.controller;

import Eduverse_backend.Mvp.translation.dto.UserLoginRequest;
import Eduverse_backend.Mvp.translation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest request){
        return  ResponseEntity.ok(authService.Login(request));
    }
}
