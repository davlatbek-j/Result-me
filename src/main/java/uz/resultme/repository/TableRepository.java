package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import uz.resultme.entity.MyTable;

public interface TableRepository extends JpaRepository<MyTable, Long>
{
    @Transactional
    void deleteByHttpUrl(String httpUrl);
}
