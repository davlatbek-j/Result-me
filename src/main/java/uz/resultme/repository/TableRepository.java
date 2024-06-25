package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.MyTable;

public interface TableRepository extends JpaRepository<MyTable, Long>
{
}
