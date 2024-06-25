package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.cases.Case;

public interface CaseRepository extends JpaRepository<Case, Long>
{

}
