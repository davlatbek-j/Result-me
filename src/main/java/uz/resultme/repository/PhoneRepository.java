package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.Phone;

public interface PhoneRepository extends JpaRepository<Phone,Long> {
}
