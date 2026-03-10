start transaction;
CREATE SCHEMA IF NOT EXISTS "keycloak";
CREATE SCHEMA IF NOT EXISTS "private";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS "private"."users"(id UUID PRIMARY KEY DEFAULT uuid_generate_v4(), username varchar(255) UNIQUE, "password" varchar(64), created_at timestamp DEFAULT (now() AT time ZONE 'utc'));
CREATE TABLE IF NOT EXISTS "public"."chats"(id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY, name varchar(100) UNIQUE, creator_id UUID REFERENCES private."users"(id));
commit;