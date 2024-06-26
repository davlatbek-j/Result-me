package uz.resultme.repository.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.resultme.entity.service.ServiceOption;

public interface ServiceOptionRepository extends JpaRepository<ServiceOption, Long>
{
    @Transactional
    @Modifying
    @Query(value = "UPDATE service_option SET table_url_uz = :tableUrlUz WHERE id = :id", nativeQuery = true)
    void updateTableUrlUz(@Param("id") Long optionId, @Param("tableUrlUz") String urlUz);

    @Transactional
    @Modifying
    @Query(value = "UPDATE service_option SET table_url_ru = :tableUrlRu WHERE id = :id", nativeQuery = true)
    void updateTableUrlRu(@Param("id") Long optionId, @Param("tableUrlRu") String urlRu);

    @Modifying
    @Transactional
    @Query(value = "UPDATE service_option SET table_url_uz = NULL, table_url_ru = NULL WHERE id = ?", nativeQuery = true)
    void clearTableUrlsById(Long id);
}
