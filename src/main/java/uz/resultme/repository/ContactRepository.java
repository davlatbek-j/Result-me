package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.resultme.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
