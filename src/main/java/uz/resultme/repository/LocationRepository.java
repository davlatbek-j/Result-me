package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>
{
}
