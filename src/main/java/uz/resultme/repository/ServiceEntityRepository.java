package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.ServiceEntity;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity,Long>
{
}
