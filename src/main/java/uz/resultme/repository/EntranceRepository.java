package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.Entrance;

public interface EntranceRepository extends JpaRepository<Entrance, Long>
{
}
