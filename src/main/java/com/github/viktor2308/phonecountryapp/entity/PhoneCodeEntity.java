package com.github.viktor2308.phonecountryapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_codes")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    UUID id;
    @Column(name = "code", nullable = false)
    Integer code;
    @Column(name = "country", nullable = false)
    String country;
}
