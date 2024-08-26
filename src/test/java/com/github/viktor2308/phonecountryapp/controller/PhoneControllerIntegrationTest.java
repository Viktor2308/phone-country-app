package com.github.viktor2308.phonecountryapp.controller;

import com.github.viktor2308.phonecountryapp.AbstractIntegrationTest;
import com.github.viktor2308.phonecountryapp.entity.PhoneCodeEntity;
import com.github.viktor2308.phonecountryapp.repository.PhoneCodeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhoneControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    PhoneCodeRepository phoneCodeRepository;

    @BeforeEach
    void setUp() {
        phoneCodeRepository.deleteAll();
        phoneCodeRepository.save(PhoneCodeEntity.builder()
                .code(1)
                .country("Canada")
                .build());
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    @SneakyThrows
    void getCountryByPhoneNumber() {

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                {
                                  "phoneNumber": "1234567890"
                                }
                                """
                )
                .when()
                .post("/api/v1/phone/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(notNullValue());
    }
}