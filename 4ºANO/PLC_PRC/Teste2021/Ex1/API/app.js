// Server that returns only JSON responses, there are no views or static pages ...
var createError = require("http-errors");
var express = require("express");
var logger = require("morgan");

// #################### ROUTES ###########################
var indexRouter = require("./routes/index");

// #################### MIDDLEWARE #######################
var app = express();

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// #################### ROUTES ###########################
app.use("/api", indexRouter);

// #################### ERROR HANDLER ####################
// catch 404 and forward to error handler
app.use(function (req, res, next) {
	next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
	// set locals, only providing error in development
	res.locals.message = err.message;
	res.locals.error = req.app.get("env") === "development" ? err : {};

	// send the error
	res.status(err.status || 500).jsonp(err);
});

// ########################################
module.exports = app;
