package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner,Long> {
    Optional<Partner> findById(Long id);
    Partner getPartnersById(Long id);

    @Override
    List<Partner> findAll();
}
