package com.github.viktor2308.phonecountryapp.service.impl;

import com.github.viktor2308.phonecountryapp.dto.CountryResponseDto;
import com.github.viktor2308.phonecountryapp.exception.NotFoundException;
import com.github.viktor2308.phonecountryapp.repository.PhoneCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {

    @Mock
    private PhoneCodeRepository phoneCodeRepository;

    @InjectMocks
    private PhoneServiceImpl phoneService;


    @Test
    void testGetCountryByPhone_Found() {
        String phoneNumber = "1234567890";
        when(phoneCodeRepository.findMaxCodeLength()).thenReturn(3);
        when(phoneCodeRepository.findCountriesByCode(123)).thenReturn(List.of("CountryA", "CountryB"));
        CountryResponseDto result = phoneService.getCountryByPhone(phoneNumber);

        assertAll(
                () -> assertEquals(2, result.getCountries().size(), "The size of the country list should be 2"),
                () -> assertTrue(result.getCountries().contains("CountryA"), "Country list should contain 'CountryA'"),
                () -> assertTrue(result.getCountries().contains("CountryB"), "Country list should contain 'CountryB'")
        );
    }

    @Test
    void testGetCountryByPhone_NotFound() {
        String phoneNumber = "9876543210";
        when(phoneCodeRepository.findMaxCodeLength()).thenReturn(3);
        when(phoneCodeRepository.findCountriesByCode(anyInt())).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> phoneService.getCountryByPhone(phoneNumber));
    }

    @Test
    void testGetMaxCodeSize_NoCodesInDatabase() {
        when(phoneCodeRepository.findMaxCodeLength()).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> phoneService.getCountryByPhone("1234567890"));
    }
}