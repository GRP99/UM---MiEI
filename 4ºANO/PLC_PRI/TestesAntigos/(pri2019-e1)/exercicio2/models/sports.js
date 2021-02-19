const mongoose = require('mongoose')


var sportSchema = new mongoose.Schema({
    resource_state: {
        type: Number
    },
    athlete: {
        id: {
            type: Number
        },
        resource_state: {
            type: Number
        }
    },
    name: {
        type: String
    },
    distance: {
        type: Number
    },
    moving_time: {
        type: Number
    },
    elapsed_time: {
        type: Number
    },
    total_elevation_gain: {
        type: Number
    },
    type: {
        type: String
    },
    id: {
        type: Number
    },
    external_id: {
        type: String
    },
    upload_id: {
        type: Number
    },
    start_date: {
        type: String
    },
    start_date_local: {
        type: String
    },
    timezone: {
        type: String
    },
    utc_offset: {
        type: Number
    },
    start_latlng: [Number],
    end_latlng: [Number],
    location_city: {
        type: String
    },
    location_state: {
        type: String
    },
    location_country: {
        type: String
    },
    start_latitude: {
        type: Number
    },
    start_longitude: {
        type: Number
    },
    achievement_count: {
        type: Number
    },
    kudos_count: {
        type: Number
    },
    comment_count: {
        type: Number
    },
    athlete_count: {
        type: Number
    },
    photo_count: {
        type: Number
    },
    map: {
        id: {
            type: String
        },
        summary_polyline: {
            type: String
        },
        resource_state: {
            type: Number
        }
    },
    trainer: {
        type: Boolean
    },
    commute: {
        type: Boolean
    },
    manual: {
        type: Boolean
    },
    private: {
        type: Boolean
    },
    visibility: {
        type: String
    },
    flagged: {
        type: Boolean
    },
    gear_id: {
        type: String
    },
    from_accepted_tag: {
        type: Boolean
    },
    average_speed: {
        type: Number
    },
    max_speed: {
        type: Number
    },
    has_heartrate: {
        type: Boolean
    },
    heartrate_opt_out: {
        type: Boolean
    },
    display_hide_heartrate_option: {
        type: Boolean
    },
    pr_count: {
        type: Number
    },
    total_photo_count: {
        type: Number
    },
    has_kudoed: {
        type: Boolean
    },
    workout_type: {
        type: Number
    },
    average_watts: {
        type: Number
    },
    kilojoules: {
        type: Number
    },
    device_watts: {
        type: Boolean
    },
    elev_high: {
        type: Number
    },
    elev_low: {
        type: Number
    }
});

module.exports = mongoose.model('sports', sportSchema)
