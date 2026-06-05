start transaction;
CREATE SCHEMA IF NOT EXISTS "keycloak";
CREATE SCHEMA IF NOT EXISTS "private";
CREATE TABLE IF NOT EXISTS "public"."chats"(id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY, name varchar(100) UNIQUE NOT NULL, creator_id varchar(36) NOT NULL REFERENCES "keycloak".user_entity(id));
commit;