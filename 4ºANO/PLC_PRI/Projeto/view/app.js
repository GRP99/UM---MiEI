var createError = require('http-errors');
var express = require('express');
var path = require('path');
var logger = require('morgan');
var jwt = require('jsonwebtoken');
var favicon = require('serve-favicon')
var cookieParser = require('cookie-parser')


// #################### ROUTES ####################
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var filesRouter = require('./routes/files');


// #################### MIDDLEWARE ####################
var app = express();
app.use(cookieParser())

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(favicon(path.join(__dirname, 'public', 'images', 'favicon.png')))
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(express.static(path.join(__dirname, 'public')));

app.use(function (req, res, next) {
    if (req.cookies.token == null) {
        switch (req.url) {
            case "/users/signup": next();
                break;
            case "/users/login": next();
                break;
            case "/favicon.ico": next();
                break;
            default: res.redirect('/users/login');
                break;
        }
    } else { // authentication
        jwt.verify(req.cookies.token, 'PRI2020', function (e, payload) {
            if (e) {
                res.redirect('/users/login');
            } else {
                req.user = {
                    level: payload.level,
                    _id: payload._id
                }
                next();
            }
        })
    }
})


// #################### ROUTES ####################
app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/files', filesRouter);


// #################### ERROR HANDLER ####################
// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) { // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('errorGIF');
});


// ########################################
module.exports = app;
