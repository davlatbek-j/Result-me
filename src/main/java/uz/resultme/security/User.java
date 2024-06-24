package uz.resultme.security;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "users")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;

    String password;

    Boolean enabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return this.enabled;
    }

    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }
}
