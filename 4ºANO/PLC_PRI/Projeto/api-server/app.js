// Servidor que retorna apenas respostas em JSON, nao existe views ou paginas estaticas...

var createError = require("http-errors");
var express = require("express");
var logger = require("morgan");
var mongoose = require("mongoose");
var cors = require("cors");
var jwt = require("jsonwebtoken");


// #################### ROUTES ####################
var usersRouter = require("./routes/users");
var filesRouter = require("./routes/files");
var newsRouter = require("./routes/news");
var searchRouter = require("./routes/search");


// #################### MONGO CONNECTION ####################
var mongoDB = "mongodb://127.0.0.1/myFiles";

mongoose.connect(mongoDB, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false
});

var db = mongoose.connection;

db.on("error", () => {
    console.log("MongoDB connection failed...");
});
db.once("open", () => {
    console.log("MongoDB connection successful...");
});


// #################### MIDDLEWARE ####################
var app = express();

/* Tratar dos ficheiros estáticos que estão na pasta public */
app.use(express.static("public/images"));

app.use(cors());
app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({extended: false}));

// verificacao da origem do pedido
app.use(function (req, res, next) {
    if (req.query.token != null) {
        jwt.verify(req.query.token, "PRI2020", function (e, payload) {
            if (e) 
                res.status(401).jsonp({error: "Token sent is invalid"});
             else {
                req.user = {
                    _id: payload._id,
                    level: payload.level
                }
                next();
            }
        });
    } else{
        res.status(401).jsonp({error: "Client did not send any token"});
    }
});


// #################### ROUTES ####################
app.use("/users", usersRouter);
app.use("/files", filesRouter);
app.use("/news", newsRouter);
app.use("/search", searchRouter);


// #################### ERROR HANDLER ####################
// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) { // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get("env") === "development" ? err : {};

    // render the error
    res.status(err.status || 500).jsonp(err);
});


// ########################################
module.exports = app;
