package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.service.ServiceEntity;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity,Long>
{

}
