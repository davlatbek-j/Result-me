package uz.resultme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.resultme.entity.service.OptionValue;

public interface OptionValueRepository extends JpaRepository<OptionValue, Long>
{

    @Transactional
    @Modifying
    @Query(value = "UPDATE option_value SET table_url_uz = :tableUrlUz WHERE id = :id", nativeQuery = true)
    void updateTableUrlUz(@Param("id") Long optionId, @Param("tableUrlUz") String urlUz);

    @Transactional
    @Modifying
    @Query(value = "UPDATE option_value SET table_url_ru = :tableUrlRu WHERE id = :id", nativeQuery = true)
    void updateTableUrlRu(@Param("id") Long optionId, @Param("tableUrlRu") String urlRu);
}
