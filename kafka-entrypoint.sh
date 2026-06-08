#!/bin/bash

set -e

echo "Starting Kafka broker..."

/etc/kafka/docker/run &

KAFKA_PID=$!

BOOTSTRAP_SERVER="localhost:9092"

echo "Waiting for Kafka..."

until /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server "$BOOTSTRAP_SERVER" \
  --list > /dev/null 2>&1
do
  echo "Kafka is not ready yet..."
  sleep 2

  if ! kill -0 "$KAFKA_PID" 2>/dev/null; then
    echo "Kafka process stopped unexpectedly."
    exit 1
  fi
done

echo "Kafka is ready. Creating topics..."

create_topic() {
  local topic_name=$1
  local partitions=$2
  local replication_factor=$3

  /opt/kafka/bin/kafka-topics.sh \
    --bootstrap-server "$BOOTSTRAP_SERVER" \
    --create \
    --if-not-exists \
    --topic "$topic_name" \
    --partitions "$partitions" \
    --replication-factor "$replication_factor"
}

create_topic "chat_create" 3 1
create_topic "created_chat_notification" 3 1
create_topic "message_create" 3 1
create_topic "notification_accepted" 3 1

echo "Topics created:"

/opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server "$BOOTSTRAP_SERVER" \
  --list

echo "Kafka init finished. Kafka is still running."

wait "$KAFKA_PID"