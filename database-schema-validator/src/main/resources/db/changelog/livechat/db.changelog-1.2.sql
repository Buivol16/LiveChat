start transaction;
CREATE EXTENSION IF NOT EXISTS pg_cron;
CREATE TABLE IF NOT EXISTS "private"."notifications"(uuid varchar(36) PRIMARY KEY , entity_id bigint NOT NULL, type varchar(255) NOT NULL, created_at Timestamp NOT NULL DEFAULT current_timestamp AT TIME ZONE 'UTC', recently_sent_at Timestamp, status varchar(255) NOT NULL);
SELECT cron.schedule('change-notification-status-after-30minutes-to-unsent','*/30 * * * *',$$UPDATE notifications SET status = 'UNACCEPTED' WHERE recently_sent_at > current_timestamp AT TIME ZONE 'UTC' - INTERVAL '30 minute' $$);
commit;