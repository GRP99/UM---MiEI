var strava = require('strava-v3');
strava.athlete.listActivities({'access_token':'58017a86f3884b77737cb548f06d0247cddb4dc0'},
    function(err,payload,limits) {
        if(!err) { console.log(payload); }
        else { console.log(err); }
    });