package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.resultme.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>
{
    Photo findByName(String name);

    Photo findByIdOrName(Long id, String name);
}
