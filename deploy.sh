#!/bin/bash
docker compose stop 

pushd springboot-service 
#./mvnw clean package || exit

popd
docker compose up --build -d

mongoimport --db analytics-dashboard --collection roles --file ./springboot-service/analytics-dashboard.roles.json --jsonArray --drop