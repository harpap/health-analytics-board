// Define the database and collections
var databaseName = "analytics-dashboard";
var collections = ["charts", "datasets", "pivots", "refreshTokens", "roles", "tables", "users", "dashboards"];

// Connect to the database
var db = db.getSiblingDB(databaseName);

collections.forEach(function(collection) {
  db.createCollection(collection);
  print("Collection '" + collection + "' created.");
});

db.getSiblingDB(databaseName).refreshTokens.createIndex({ "expiryDate": 1 }, { expireAfterSeconds: 0 });