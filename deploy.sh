#!/bin/bash
docker compose stop 

pushd springboot-service 
mvn clean install

popd
docker compose up --build -d

mongoimport --db analytics-dashboard --collection roles --file ./springboot-service/analytics-dashboard.roles.json --jsonArray --drop

mongoimport --db analytics-dashboard --collection datasets --file "./Data/Environmental Data/eu_env_pollutants_agg.json" --jsonArray --drop
mongoimport --db analytics-dashboard --collection datasets --file "Data/Environmental Data/eu_env_pollutants_all.json" --jsonArray --drop

mongoimport --db analytics-dashboard --collection datasets --type csv --headerline --file "Data/Behavioral/daily_smokers_per_income.csv"
mongoimport --db analytics-dashboard --collection datasets --type csv --headerline --file "Data/Behavioral/fruit_portions_and_activity.csv"
mongoimport --db analytics-dashboard --collection datasets --type csv --headerline --file "Data/Behavioral/fruit_portions.csv"
mongoimport --db analytics-dashboard --collection datasets --type csv --headerline --file "Data/Behavioral/fruits_activity_crc_meat.csv"
mongoimport --db analytics-dashboard --collection datasets --type csv --headerline --file "Data/Behavioral/physical_activity.csv"
mongoimport --db analytics-dashboard --collection datasets --file "Data/Behavioral/fruits_activity_crc_meat.json" --jsonArray

mongoimport --db analytics-dashboard --collection datasets --file "Data/CRC Incidence Rates/eu_crc_incidents.json" --jsonArray
mongoimport --db analytics-dashboard --collection datasets --file "Data/CRC Incidence Rates/global_crc_incidents.json" --jsonArray
mongoimport --db analytics-dashboard --collection datasets --file "Data/CRC Incidence Rates/list_of_eu_countries.json" --jsonArray