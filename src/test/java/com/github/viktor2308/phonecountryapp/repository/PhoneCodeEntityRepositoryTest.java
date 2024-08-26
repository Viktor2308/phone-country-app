package com.github.viktor2308.phonecountryapp.repository;

import com.github.viktor2308.phonecountryapp.AbstractIntegrationTest;
import com.github.viktor2308.phonecountryapp.entity.PhoneCodeEntity;
import com.github.viktor2308.phonecountryapp.service.impl.WikiDataInitServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneCodeEntityRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PhoneCodeRepository phoneCodeRepository;

    @MockBean
    WikiDataInitServiceImpl wikiDataInitService;

    @BeforeEach
    void init() {
        phoneCodeRepository.deleteAll();

        phoneCodeRepository.save(PhoneCodeEntity.builder()
                .code(1)
                .country("One")
                .build()
        );
        phoneCodeRepository.save(PhoneCodeEntity.builder()
                .code(123)
                .country("OneTwoThree")
                .build()
        );
    }

    @Test
    void findByCodeAndCountry() {
        PhoneCodeEntity entity = phoneCodeRepository.findByCodeAndCountry(1, "One");
        assertAll(
                () ->
                        assertEquals(1, entity.getCode(), "Code should be 1"),
                () ->
                        assertEquals("One", entity.getCountry(), "Country should be One")
        );
    }

    @Test
    void findCountryByCode() {
        List<String> country = phoneCodeRepository.findCountriesByCode(1);
        assertEquals(1, country.size(), "Size should be 1");
    }

    @Test
    void findMaxCodeLength() {
        Integer maxCodeSize = phoneCodeRepository.findMaxCodeLength();
        assertEquals(3, maxCodeSize, "Max size should be 3");
    }

    @AfterEach
    void clearDbAfterTest() {
        phoneCodeRepository.deleteAll();
    }
}