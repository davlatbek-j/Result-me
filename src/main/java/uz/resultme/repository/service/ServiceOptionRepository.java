package uz.resultme.repository.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.resultme.entity.service.ServiceOption;

public interface ServiceOptionRepository extends JpaRepository<ServiceOption, Long>
{
}
