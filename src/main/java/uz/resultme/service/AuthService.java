package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.SignIn;
import uz.resultme.repository.UserRepository;
import uz.resultme.security.JwtTokenService;
import uz.resultme.security.User;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService tokenService;

    public ResponseEntity<ApiResponse<String>> login(SignIn signIn)
    {
        User byLogin = userRepository.findByUsername(signIn.getUsername());
        if (byLogin != null)
        {
            if (passwordEncoder.matches(signIn.getPassword(), byLogin.getPassword()))
            {
                ApiResponse<String> response = new ApiResponse<>();
                response.setMessage("Login successful");
                response.setData("Token: "+tokenService.generateToken(signIn.getUsername()));
                return ResponseEntity.ok(response);
            }
        }
        return null;
    }

}
