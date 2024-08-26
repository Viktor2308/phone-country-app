package com.github.viktor2308.phonecountryapp.service;

import com.github.viktor2308.phonecountryapp.dto.CountryResponseDto;

public interface PhoneService {

    CountryResponseDto getCountryByPhone(String phoneNumber);
}
