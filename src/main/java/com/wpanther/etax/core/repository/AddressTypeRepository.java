package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.AddressType;

import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Address Type Codes
 *
 * Provides query methods for address type classifications:
 * - Postal address (1): Representing a postal address
 * - Fiscal address (2): Required by fiscal administrations
 * - Physical address (3): Actual physical location
 */
@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, String> {

    /**
     * Find address type by code
     */
    @Query("SELECT a FROM AddressType a WHERE a.code = :code")
    Optional<AddressType> findByCode(@Param("code") String code);

    /**
     * Find postal address type (code 1)
     */
    @Query("SELECT a FROM AddressType a WHERE a.code = '1'")
    Optional<AddressType> findPostalAddress();

    /**
     * Find fiscal address type (code 2)
     */
    @Query("SELECT a FROM AddressType a WHERE a.code = '2'")
    Optional<AddressType> findFiscalAddress();

    /**
     * Find physical address type (code 3)
     */
    @Query("SELECT a FROM AddressType a WHERE a.code = '3'")
    Optional<AddressType> findPhysicalAddress();

    /**
     * Check if address type code exists
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AddressType a WHERE a.code = :code")
    boolean existsByCode(@Param("code") String code);
}
