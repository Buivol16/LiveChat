start transaction;
CREATE TABLE public.avatars(id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, user_id varchar(36) NOT NULL REFERENCES "keycloak".user_entity(id), file_name varchar(255));
commit;