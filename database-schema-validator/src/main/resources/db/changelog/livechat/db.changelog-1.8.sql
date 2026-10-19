start transaction;
ALTER TABLE "public".avatars ADD is_using bool DEFAULT false NOT NULL;
ALTER TABLE "public".avatars ADD uploaded_at TIMESTAMP DEFAULT now() AT TIME ZONE 'UTC' NOT NULL;
commit;