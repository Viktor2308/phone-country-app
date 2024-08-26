package com.github.viktor2308.phonecountryapp.controller;

import com.github.viktor2308.phonecountryapp.dto.CountryResponseDto;
import com.github.viktor2308.phonecountryapp.dto.PhoneNumberRequestDto;
import com.github.viktor2308.phonecountryapp.service.PhoneService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/phone")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneController {

    final PhoneService phoneService;

    @PostMapping("/")
    public CountryResponseDto getCountryByPhoneNumber(@RequestBody PhoneNumberRequestDto phoneNumber) {
        log.info("Request get country by phone: {}", phoneNumber.getPhoneNumber());
        return phoneService.getCountryByPhone(phoneNumber.getPhoneNumber());
    }
}
