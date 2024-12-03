db.createUser({
  user: "techchallenge",
  pwd: "techchallenge",
  roles: [
    { role: "readWrite", db: "paymentdb" }
  ]
});

db = db.getSiblingDB("local");

db.createCollection("pagamentos");