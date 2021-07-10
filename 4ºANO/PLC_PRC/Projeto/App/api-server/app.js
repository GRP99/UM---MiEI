// Server that returns only JSON responses, there are no views or static pages ...
var cookieParser = require("cookie-parser");
var createError = require("http-errors");
var jwt = require("jsonwebtoken");
var express = require("express");
var logger = require("morgan");
var cors = require("cors");
var mongoose = require("mongoose");

// #################### ROUTES ###########################
var adminRouter = require("./routes/admin");
var barsRouter = require("./routes/bars");
var bartendersRouter = require("./routes/bartenders");
var categoriesRouter = require("./routes/categories");
var cocktailsRouter = require("./routes/cocktails");
var dbcocktailsRouter = require("./routes/db_cocktails");
var dbbartendersRouter = require("./routes/db_bartenders");
var dblocationsRouter = require("./routes/db_locations");
var dbbarsRouter = require("./routes/db_bars");
var glasswaresRouter = require("./routes/glasswares");
var ingredientsRouter = require("./routes/ingredients");
var locationsRouter = require("./routes/locations");
var queriesRouter = require("./routes/queries");
var userRouter = require("./routes/users");

// #################### MONGO CONNECTION ####################
var mongoDB = "mongodb://127.0.0.1/cocktailsDB";

mongoose.connect(mongoDB, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false,
});

var db = mongoose.connection;

db.on("error", () => {
    console.log("MongoDB connection failed...");
});
db.once("open", () => {
    console.log("MongoDB connection successful...");
});

// #################### MIDDLEWARE #######################
var app = express();
app.use(cors());
app.use(logger("dev"));
app.use(express.json());
app.use(cookieParser());
app.use(express.urlencoded({
    extended: false
}));

app.use(function(req, res, next) {
    if (req.query.token == null) {
        req.user = {
            level: "none"
        };
        next();
    } else {
        // authentication
        jwt.verify(req.query.token, "PRC2021", function(e, payload) {
            if (e) {
                res.status(401).jsonp({
                    error: "Token sent is invalid !"
                });
            } else {
                req.user = {
                    level: payload.level,
                    _id: payload._id
                };
                next();
            }
        });
    }
});

// #################### ROUTES ###########################
app.use("/admin", adminRouter);
app.use("/bars", barsRouter);
app.use("/bartenders", bartendersRouter);
app.use("/categories", categoriesRouter);
app.use("/cocktails", cocktailsRouter);
app.use("/dbcocktails", dbcocktailsRouter);
app.use("/dbbartenders", dbbartendersRouter);
app.use("/dblocations", dblocationsRouter);
app.use("/dbbars", dbbarsRouter);
app.use("/glasswares", glasswaresRouter);
app.use("/ingredients", ingredientsRouter);
app.use("/locations", locationsRouter);
app.use("/users", userRouter);
app.use("/", queriesRouter);


// #################### ERROR HANDLER ####################
// catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get("env") === "development" ? err : {};

    // send the error
    res.status(err.status || 500).jsonp(err);
});

// ########################################
module.exports = app;