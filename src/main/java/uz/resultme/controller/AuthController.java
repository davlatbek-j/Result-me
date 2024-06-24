package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.AuthResponse;
import uz.resultme.payload.SignIn;
import uz.resultme.service.AuthService;

@RequiredArgsConstructor

@RequestMapping("/auth")
@Controller
public class AuthController
{
    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody SignIn signIn)
    {
        return authService.login(signIn);
    }
}
