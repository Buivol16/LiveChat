start transaction;
CREATE TABLE IF NOT EXISTS "private"."chats"(id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY, creator_id varchar(36) NOT NULL REFERENCES "keycloak".user_entity(id), partner_id varchar(36) NOT NULL REFERENCES "keycloak".user_entity(id), created_at Timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'::text), deleted_for_creator boolean NOT NULL DEFAULT false, deleted_for_partner boolean NOT NULL DEFAULT false);
ALTER TABLE "public".chats ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'::text);
commit;