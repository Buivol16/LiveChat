ALTER TABLE "private"."messages" ADD COLUMN is_read boolean not null default false;
ALTER TABLE private.messages ALTER COLUMN receiver_id DROP NOT NULL;