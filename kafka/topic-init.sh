#!/bin/bash
# Script de inicialización de topics Kafka

# Espera unos segundos para que Kafka esté completamente iniciado
sleep 5

echo "Creando topics de Kafka..."

# Lista de topics que se crearán automáticamente
TOPICS=(
  "facturacion-events"
  "inventario-events"
  "notificacion-mail"
  "proveedor-notifications"
  "usuarios"
  "sistema-logs"
)

for topic in "${TOPICS[@]}"
do
  kafka-topics.sh --create \
    --if-not-exists \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 3 \
    --topic "$topic"

  echo "Topic creado: $topic"
done

echo "✅ Todos los topics han sido creados exitosamente."
