import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Home.vue')
    },

    {
        path: "/monitor/users/",
        name: 'Users',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Users.vue')
    },
    {
        path: "/tablespaces",
        name: 'Tablespaces',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Tablespaces.vue')
    },
    {
        path: "/tablespace/:id/:id2",
        name: 'Tablespace',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Tablespace.vue')
    }, {
        path: "/datafiles",
        name: 'Datafiles',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Datafiles.vue')
    }, {
        path: "/datafile/:id/:id2",
        name: 'Datafile',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Datafile.vue')
    },
    // THIS
    {
        path: "/cpu/SYS",
        name: 'cpuSYS',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/CpuSYS.vue')
    }, {
        path: "/cpu/SYSTEM",
        name: 'cpuSYSTEM',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/CpuSYSTEM.vue')
    }, {
        path: "/SGA",
        name: 'SGA',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Sga.vue')
    }, {
        path: "/PGA",
        name: 'PGA',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Pga.vue')
    }, {
        path: "/sessions",
        name: 'Sessions',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Sessions.vue')
    }, {
        path: "/session/:id/:id2",
        name: 'Session',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/Session.vue')
    }, {
        path: "/user/details/:id/:id2",
        name: 'details',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/userDetails.vue')
    }, {
        path: "/about",
        name: 'about',
        component: () => import ( /* webpackChunkName: "about" */
                '../views/About.vue')
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    }
})

export default router