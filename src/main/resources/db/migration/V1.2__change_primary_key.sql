ALTER TABLE technology_profile
ADD COLUMN id BIGSERIAL;

ALTER TABLE technology_profile
DROP CONSTRAINT profile_technology_pkey;

ALTER TABLE technology_profile
ADD CONSTRAINT profile_technology_pkey PRIMARY KEY (id);

ALTER TABLE technology_profile
ADD CONSTRAINT unique_technology_profile UNIQUE (technology_id, profile_id);

-- REVERT
-- ALTER TABLE profile_technology DROP COLUMN id;
-- ALTER TABLE profile_technology DROP CONSTRAINT profile_technology_pkey;
-- ALTER TABLE profile_technology ADD CONSTRAINT profile_technology_pkey PRIMARY KEY (technology_id, profile_id);
-- ALTER TABLE profile_technology DROP CONSTRAINT unique_technology_profile;
-- DELETE FROM flyway_schema_history WHERE version = '1.2';