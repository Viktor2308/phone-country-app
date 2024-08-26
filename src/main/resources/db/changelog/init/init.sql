
CREATE TABLE phone_codes
(
    id      UUID         NOT NULL,
    code    INTEGER      NOT NULL,
    country VARCHAR(170) NOT NULL,
    CONSTRAINT pk_phone_codes PRIMARY KEY (id),
    CONSTRAINT unique_code_country UNIQUE (code, country)
);

CREATE INDEX idx_phone_codes_code ON phone_codes (code);

CREATE INDEX idx_phone_codes_code_country ON phone_codes (code, country);