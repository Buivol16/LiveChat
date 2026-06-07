#!/usr/bin/env bash
set -Eeuo pipefail

PGDATA="${PGDATA:-/var/lib/postgresql/data}"
PGPORT="${PGPORT:-5432}"

POSTGRES_USER="${POSTGRES_USER:-postgres}"
POSTGRES_DB="${POSTGRES_DB:-$POSTGRES_USER}"

PGCRON_DATABASE_NAME="${PGCRON_DATABASE_NAME:-$POSTGRES_DB}"

add_or_replace_conf() {
  local file="$1"
  local key="$2"
  local value="$3"

  if grep -Eq "^[[:space:]]*#?[[:space:]]*${key}[[:space:]]*=" "$file"; then
    sed -ri "s|^[[:space:]]*#?[[:space:]]*${key}[[:space:]]*=.*|${key} = ${value}|" "$file"
  else
    printf "\n%s = %s\n" "$key" "$value" >> "$file"
  fi
}

install_pg_cron() {
  echo "Checking pg_cron package..."

  if dpkg -s postgresql-17-cron > /dev/null 2>&1; then
    echo "postgresql-17-cron already installed. Skipping."
    return 0
  fi

  echo "Installing postgresql-17-cron..."

  apt-get update
  apt-get -y install postgresql-17-cron
  rm -rf /var/lib/apt/lists/*

  echo "postgresql-17-cron installed."
}

configure_pg_cron() {
  local conf_file

  if [ -s "$PGDATA/PG_VERSION" ]; then
    conf_file="$PGDATA/postgresql.conf"
  else
    conf_file="$(pg_config --sharedir)/postgresql.conf.sample"
  fi

  echo "Configuring pg_cron in: $conf_file"

  add_or_replace_conf "$conf_file" "shared_preload_libraries" "'pg_cron'"
  add_or_replace_conf "$conf_file" "cron.database_name" "'$PGCRON_DATABASE_NAME'"
}

wait_for_postgres() {
  echo "Waiting for PostgreSQL to accept TCP connections..."

  until pg_isready \
    -h 127.0.0.1 \
    -p "$PGPORT" \
    -U "$POSTGRES_USER" \
    -d "$PGCRON_DATABASE_NAME" > /dev/null 2>&1
  do
    sleep 1
  done

  echo "PostgreSQL is ready."
}

ensure_pg_cron_extension() {
  echo "Checking pg_cron extension..."

  export PGPASSWORD="${POSTGRES_PASSWORD:-}"

  local extension_exists

  extension_exists="$(
    psql \
      -h 127.0.0.1 \
      -p "$PGPORT" \
      -U "$POSTGRES_USER" \
      -d "$PGCRON_DATABASE_NAME" \
      -tAc "SELECT 1 FROM pg_extension WHERE extname = 'pg_cron';"
  )"

  if [ "$extension_exists" = "1" ]; then
    echo "pg_cron extension already exists. Skipping."
    return 0
  fi

  echo "pg_cron extension does not exist. Creating..."

  psql \
    -h 127.0.0.1 \
    -p "$PGPORT" \
    -U "$POSTGRES_USER" \
    -d "$PGCRON_DATABASE_NAME" \
    -v ON_ERROR_STOP=1 \
    -v db_user="$POSTGRES_USER" <<'SQL'
CREATE EXTENSION IF NOT EXISTS pg_cron;

GRANT USAGE ON SCHEMA cron TO :"db_user";
SQL

  echo "pg_cron extension created."
}

main() {
  install_pg_cron

  configure_pg_cron

  docker-entrypoint.sh "$@" &
  postgres_pid="$!"

  wait_for_postgres
  ensure_pg_cron_extension

  wait "$postgres_pid"
}

main "$@"