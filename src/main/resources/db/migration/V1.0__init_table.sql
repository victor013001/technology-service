CREATE TABLE technology (
	id BIGSERIAL NOT NULL,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(90) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE profile_technology (
	technology_id BIGINT NOT NULL,
	profile_id BIGINT NOT NULL,
	PRIMARY KEY(technology_id, profile_id),
	FOREIGN KEY (technology_id) REFERENCES technology(id)
);

-- REVERT
-- DROP TABLE IF EXISTS technology;
-- DROP TABLE IF EXISTS profile_technology;
-- DELETE FROM flyway_schema_history WHERE version = '1.0';