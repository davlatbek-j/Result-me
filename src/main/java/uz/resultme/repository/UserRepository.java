package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.security.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
