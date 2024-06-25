package uz.resultme.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.resultme.repository.UserRepository;
import uz.resultme.service.UserService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${jwt.token.secretKey}")
    private String secretKey;
    @Value("${jwt.token.expireDateInMilliSeconds}")
    private Long expireDate;

    public String generateToken(String username) {
        Date now = new Date();
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireDate))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.err.println("\nToken generated : " + token);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return  true ;
        }catch (ExpiredJwtException e) {
            System.err.println("Muddati o'tgan");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Buzilgan token");
        } catch (SignatureException s) {
            System.err.println("Kalit so'z xato");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Qo'llanilmagan token");
        } catch (IllegalArgumentException ex) {
            System.err.println("Bo'sh token");
        }
        return false;
    }

    public User getUserFromToken(String token)
    {
        try {
            String subject = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return userRepository.findByUsername(subject);
        }catch (Exception e) {
            throw new RuntimeException("Authorization failed. Please log in again");
        }
    }

}
