package com.github.viktor2308.phonecountryapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viktor2308.phonecountryapp.dto.CountryResponseDto;
import com.github.viktor2308.phonecountryapp.dto.PhoneNumberRequestDto;
import com.github.viktor2308.phonecountryapp.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
class PhoneControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneService phoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetCountryByPhoneNumber() throws Exception {
        String phoneNumber = "1234567890";
        PhoneNumberRequestDto phoneNumberRequestDto = PhoneNumberRequestDto.builder().phoneNumber(phoneNumber).build();
        CountryResponseDto expectedCountryResponseDto = CountryResponseDto.builder().countries(List.of("TestCountry")).build();

        when(phoneService.getCountryByPhone(phoneNumber)).thenReturn(expectedCountryResponseDto);

        mockMvc.perform(post("/api/v1/phone/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneNumberRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCountryResponseDto)));
    }
}