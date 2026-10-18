start transaction;
ALTER TABLE "public".avatars ALTER COLUMN user_id DROP NOT NULL;
ALTER TABLE "public".avatars ADD "chat_id" bigint REFERENCES "public".chats(id);
commit;