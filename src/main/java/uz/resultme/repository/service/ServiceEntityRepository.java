package uz.resultme.repository.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.resultme.entity.service.ServiceEntity;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity,Long>
{

}
