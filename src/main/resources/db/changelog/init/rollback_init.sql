ALTER TABLE phone_codes
    DROP CONSTRAINT IF EXISTS unique_code_country;
DROP TABLE phone_codes;

