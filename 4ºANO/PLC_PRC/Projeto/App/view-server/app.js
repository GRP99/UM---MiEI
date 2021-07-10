var cookieParser = require('cookie-parser');
var createError = require("http-errors");
var favicon = require('serve-favicon');
var jwt = require("jsonwebtoken");
var express = require("express");
var logger = require("morgan");
var path = require('path');
var cors = require('cors');

// #################### ROUTES ####################
var adminRouter = require("./routes/admin");
var indexRouter = require('./routes/index');
var manageRouter = require("./routes/manage");
var userRouter = require("./routes/users");

// #################### MIDDLEWARE ####################
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(cors());
app.use(cookieParser());
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({
    extended: false
}));
app.use(express.static(path.join(__dirname, 'public')));
app.use(favicon(path.join(__dirname, 'public', 'images', 'favicon.ico')));

app.use(function(req, res, next) {
    if (req.cookies.token == null) {
        req.user = { level: 'none' }
        next();
    } else {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = { level: payload.level, _id: payload._id }
                next();
            }
        })
    }
});

// #################### ROUTES ####################
app.use('/admin/manage', manageRouter);
app.use('/admin', adminRouter);
app.use('/users', userRouter);
app.use('/', indexRouter);

// #################### ERROR HANDLER ####################
// catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) { // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error', {
        text: "The page you are looking for not avaible!",
        error: err
    });
});


// ########################################
module.exports = app;