package com.wpanther.etax.repository;

import com.wpanther.etax.entity.ThaiProvinceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Thai Province Codes
 */
@Repository
public interface ThaiProvinceCodeRepository extends JpaRepository<ThaiProvinceCode, String> {

    /**
     * Find province by code and active status
     */
    @Query("SELECT p FROM ThaiProvinceCode p WHERE p.code = :code AND p.active = true")
    Optional<ThaiProvinceCode> findByCodeAndActive(@Param("code") String code);

    /**
     * Find all active provinces
     */
    List<ThaiProvinceCode> findByActiveTrue();

    /**
     * Find provinces by region
     */
    @Query("SELECT p FROM ThaiProvinceCode p WHERE p.region = :region AND p.active = true ORDER BY p.nameTh")
    List<ThaiProvinceCode> findByRegion(@Param("region") String region);

    /**
     * Search provinces by Thai name (case-insensitive)
     */
    @Query("SELECT p FROM ThaiProvinceCode p WHERE p.nameTh LIKE %:name% AND p.active = true")
    List<ThaiProvinceCode> findByNameThContaining(@Param("name") String name);

    /**
     * Search provinces by English name (case-insensitive)
     */
    @Query("SELECT p FROM ThaiProvinceCode p WHERE UPPER(p.nameEn) LIKE UPPER(CONCAT('%', :name, '%')) AND p.active = true")
    List<ThaiProvinceCode> findByNameEnContaining(@Param("name") String name);

    /**
     * Check if province code exists and is active
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ThaiProvinceCode p WHERE p.code = :code AND p.active = true")
    boolean existsByCodeAndActive(@Param("code") String code);
}
