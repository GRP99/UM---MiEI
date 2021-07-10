import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import(/* webpackChunkName: "home" */ '../views/Home.vue')
  },
  {
    path: '/pubs',
    name: 'Pubs',
    component: () => import(/* webpackChunkName: "pubs" */ '../views/Pubs.vue')
  },
  {
    path: '/pubs/:idPub',
    name: 'Pub',
    component: () => import(/* webpackChunkName: "pub" */ '../views/Pub.vue')
  },
  {
    path: '/authors',
    name: 'Authors',
    component: () => import(/* webpackChunkName: "authors" */ '../views/Authors.vue')
  },
  {
    path: '/authors/:idAuthor',
    name: 'Author',
    component: () => import(/* webpackChunkName: "author" */ '../views/Author.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
