// Server that returns only JSON responses, there are no views or static pages ...
var createError = require("http-errors");
var express = require("express");
var logger = require("morgan");
var mongoose = require("mongoose");
const swaggerUI = require("swagger-ui-express");
const swaggerJsDoc = require("swagger-jsdoc");

const options = {
	definition: {
		openapi: "3.0.0",
		info: {
			title: "EHR REST API",
			version: "1.0.0",
			description: "Electronic Health Records (EHR) management system based on the OpenEHR clinical model.",
            contact:{
                name: "Grupo 1",
                email: "a83732@alunos.uminho.pt"
            },
            license: {
                name: "MIT",
                ur: "https://opensource.org/licenses/MIT"
            }
		},
		servers: [
			{
				url: "http://localhost:2021",
			},
		],
	},
	apis: ["./routes/*.js"],
};
const specs = swaggerJsDoc(options);

// #################### ROUTES ###########################
var ehrRouter = require("./routes/ehr");
var contributionRouter = require("./routes/contribution");
var directoryRouter = require("./routes/directory");

// #################### MONGO CONNECTION #################
var mongoDB = "mongodb://127.0.0.1/ehrManagement";

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

app.use("/api-docs", swaggerUI.serve, swaggerUI.setup(specs));

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({
    extended: false
}));

// #################### ROUTES ###########################
app.use("/ehr/:ehr_id/directory", directoryRouter);
app.use("/ehr/:ehr_id/contribution", contributionRouter);
app.use("/ehr", ehrRouter);

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

    // render the error page
    res.status(err.status || 500).jsonp(err);
});
// #######################################################
module.exports = app;