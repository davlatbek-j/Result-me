package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<ApiResponse<?>> login(SignIn signIn)
    {
        User byLogin = userRepository.findByUsername(signIn.getUsername());
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        if (byLogin != null)
        {
            if (passwordEncoder.matches(signIn.getPassword(), byLogin.getPassword()))
            {
                response.setMessage("Login successful");
                response.setData(new AuthResponse(tokenService.generateToken(signIn.getUsername())));
                return ResponseEntity.ok(response);
            }
        }
        response.setMessage("Username or password incorrect");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}

class AuthResponse
{
    private String token;

    public AuthResponse()
    {
    }

    public AuthResponse(String token)
    {
        this.token = token;
    }
}
