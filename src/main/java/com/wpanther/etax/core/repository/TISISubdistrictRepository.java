package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.TISISubdistrict;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for TISI Subdistricts
 */
@Repository
public interface TISISubdistrictRepository extends JpaRepository<TISISubdistrict, String> {

    /**
     * Find subdistrict by code
     */
    Optional<TISISubdistrict> findByCode(String code);

    /**
     * Find subdistricts by province code (first 2 digits)
     */
    @Query("SELECT s FROM TISISubdistrict s WHERE s.provinceCode = :provinceCode ORDER BY s.code")
    List<TISISubdistrict> findByProvinceCode(@Param("provinceCode") String provinceCode);

    /**
     * Find subdistricts by city code (first 4 digits)
     */
    @Query("SELECT s FROM TISISubdistrict s WHERE s.cityCode = :cityCode ORDER BY s.code")
    List<TISISubdistrict> findByCityCode(@Param("cityCode") String cityCode);

    /**
     * Search subdistricts by Thai name
     */
    @Query("SELECT s FROM TISISubdistrict s WHERE s.nameTh LIKE %:name% ORDER BY s.code")
    List<TISISubdistrict> findByNameThContaining(@Param("name") String name);

    /**
     * Check if subdistrict code exists
     */
    boolean existsByCode(String code);

    /**
     * Count subdistricts by province
     */
    @Query("SELECT COUNT(s) FROM TISISubdistrict s WHERE s.provinceCode = :provinceCode")
    long countByProvinceCode(@Param("provinceCode") String provinceCode);

    /**
     * Count subdistricts by city
     */
    @Query("SELECT COUNT(s) FROM TISISubdistrict s WHERE s.cityCode = :cityCode")
    long countByCityCode(@Param("cityCode") String cityCode);
}
