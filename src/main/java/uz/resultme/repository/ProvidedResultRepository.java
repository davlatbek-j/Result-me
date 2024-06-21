package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.ProvidedResult;

public interface ProvidedResultRepository extends JpaRepository<ProvidedResult,Long>
{

}
