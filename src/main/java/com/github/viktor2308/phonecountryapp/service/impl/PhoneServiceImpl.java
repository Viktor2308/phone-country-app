package com.github.viktor2308.phonecountryapp.service.impl;

import com.github.viktor2308.phonecountryapp.dto.CountryResponseDto;
import com.github.viktor2308.phonecountryapp.exception.NotFoundException;
import com.github.viktor2308.phonecountryapp.repository.PhoneCodeRepository;
import com.github.viktor2308.phonecountryapp.service.PhoneService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneServiceImpl implements PhoneService {

    final PhoneCodeRepository phoneCodeRepository;

    @Override
    public CountryResponseDto getCountryByPhone(String phoneNumber) {
        Integer maxCodeSize = getMaxCodeSize();

        List<String> countries = findCountriesForPhoneNumber(phoneNumber, maxCodeSize);


        return Optional.of(countries)
                .filter(list -> !list.isEmpty())
                .map(countriesList -> CountryResponseDto.builder()
                        .countries(countriesList)
                        .build())
                .orElseThrow(() -> new NotFoundException("No countries found for the provided phone number."));
    }

    private Integer getMaxCodeSize() {
        return Optional.ofNullable(phoneCodeRepository.findMaxCodeLength())
                .filter(size -> size > 0)
                .orElseThrow(() -> new IllegalStateException("No phone codes found in the database."));
    }

    private Integer getCodeForSize(String phoneNumber, int size) {
        try {
            return Integer.parseInt(phoneNumber.substring(0, size));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private List<String> findCountriesForPhoneNumber(String phoneNumber, Integer maxCodeSize) {
        for (int size = maxCodeSize; size > 0; size--) {
            Integer code = getCodeForSize(phoneNumber, size);
            List<String> foundCountries = phoneCodeRepository.findCountriesByCode(code);
            if (!foundCountries.isEmpty()) {
                return foundCountries;
            }
        }
        return Collections.emptyList();
    }
}
