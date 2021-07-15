const mysql = require("mysql");

var mysqlConnection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "12345",
    port: 3306,
    database: "appb"
});

mysqlConnection.connect((err) => {
    if (!err) {
        console.log("Connected to the Database");
    } else {
        console.log("Database Connection Failed");
    }
});

module.exports = mysqlConnection;