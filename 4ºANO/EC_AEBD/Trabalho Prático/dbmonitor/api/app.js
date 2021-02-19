var createError = require('http-errors');
var express = require('express');
var logger = require('morgan');

var cors = require('cors')
const corsOpts = {
    origin: '*',
    credentials: true,
    methods: [
        'GET',
        'PUT',
        'POST',
        'DELETE',
        'OPTIONS'
    ],
    allowedHeaders: [
        'Accept',
        'Authorization',
        'Cache-Control',
        'Content-Type',
        'DNT',
        'If-Modified-Since',
        'Keep-Alive',
        'Origin',
        'User-Agent',
        'X-Requested-With',
        'Content-Length'
    ]
}

var indexRouter = require('./routes/index');

var app = express();

// view engine setup

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cors(corsOpts));
app.options('*', cors(corsOpts));

app.use('/', indexRouter);
// Make '/' indexRouter path

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
    res.render('error');
});

module.exports = app;