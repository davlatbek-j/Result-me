package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.service.MyTable;

public interface TableRepository extends JpaRepository<MyTable, Long>
{

}
