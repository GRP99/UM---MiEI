import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import Router from "vue-router"
Vue.use(Router)

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => {
        if (err.name !== 'NavigationDuplicated') 
            throw err
    });
}

var moment = require('moment');

Vue.config.productionTip = false

new Vue({
    router,
    vuetify,
    moment: moment,
    render: h => h(App)
}).$mount('#app')