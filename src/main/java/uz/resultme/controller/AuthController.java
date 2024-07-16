package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.SignIn;
import uz.resultme.service.AuthService;

@RequiredArgsConstructor

@RequestMapping("/auth")
@Controller
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody SignIn signIn)
    {
        return authService.login(signIn);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test()
    {
        return ResponseEntity.ok("This is a test , All right?? Sabr please....");
    }

}
