// NEWS CONTROLLER

var News = require('../models/news');

// retorna all the news
module.exports.list = () => {
    return News.find().sort({"date": -1}).exec()
}

// return the last 5 news
module.exports.last5News = () => {
    return News.find().sort({"date": -1}).limit(5).exec()
}

// return only one new
module.exports.lookup = id => {
    return News.findOne({_id: id}).exec()
}

// record new news
module.exports.insert = (news) => {
    var newNews = new News(news)
    return newNews.save()
}
