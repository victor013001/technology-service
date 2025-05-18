ALTER TABLE profile_technology RENAME TO technology_profile;

-- REVERT
-- ALTER TABLE technology_profile RENAME TO profile_technology;
-- DELETE FROM flyway_schema_history WHERE version = '1.1';