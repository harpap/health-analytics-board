#!/bin/bash
sudo docker compose stop

pushd springboot-service
./mvnw clean package || exit

popd
sudo docker compose up --build -d