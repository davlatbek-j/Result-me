package uz.resultme.aa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.resultme.repository.UserRepository;
import uz.resultme.security.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner
{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception
    {
        if (userRepository.existsByUsername("molochay"))
            return;
        User user = new User();
        user.setUsername("molochay");
        user.setPassword(passwordEncoder.encode("off@onn"));
        userRepository.save(user);
    }
}
