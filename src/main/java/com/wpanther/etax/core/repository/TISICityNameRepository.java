package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.TISICityName;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for TISI City Names
 */
@Repository
public interface TISICityNameRepository extends JpaRepository<TISICityName, String> {

    /**
     * Find city by code
     */
    Optional<TISICityName> findByCode(String code);

    /**
     * Find cities by province code (first 2 digits)
     */
    @Query("SELECT c FROM TISICityName c WHERE c.provinceCode = :provinceCode ORDER BY c.code")
    List<TISICityName> findByProvinceCode(@Param("provinceCode") String provinceCode);

    /**
     * Search cities by Thai name (case-insensitive)
     */
    @Query("SELECT c FROM TISICityName c WHERE c.nameTh LIKE %:name% ORDER BY c.code")
    List<TISICityName> findByNameThContaining(@Param("name") String name);

    /**
     * Check if city code exists
     */
    boolean existsByCode(String code);

    /**
     * Count cities by province
     */
    @Query("SELECT COUNT(c) FROM TISICityName c WHERE c.provinceCode = :provinceCode")
    long countByProvinceCode(@Param("provinceCode") String provinceCode);
}
