start transaction;
CREATE TABLE public.invitelinks(uuid varchar(36) NOT NULL, chat_id bigint NOT NULL REFERENCES "public".chats(id));
CREATE TABLE public.members(user_id varchar(36) NOT NULL REFERENCES "keycloak".user_entity(id), chat_id bigint NOT NULL REFERENCES "public".chats(id));
ALTER TABLE public.chats ADD description varchar(500) NULL;
commit;