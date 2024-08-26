package com.github.viktor2308.phonecountryapp.repository;

import com.github.viktor2308.phonecountryapp.entity.PhoneCodeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PhoneCodeRepository extends JpaRepository<PhoneCodeEntity, UUID> {

    PhoneCodeEntity findByCodeAndCountry(Integer code, String country);

    @Query("SELECT p.country FROM PhoneCodeEntity p WHERE p.code = :code")
    List<String> findCountriesByCode(@Param("code") Integer code);

    @Cacheable("codeSize")
    @Query(value = "SELECT MAX(LENGTH(CAST(code AS TEXT))) FROM phone_codes", nativeQuery = true)
    Integer findMaxCodeLength();
}