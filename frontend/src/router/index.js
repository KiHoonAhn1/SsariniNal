import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import Login from '@/views/accounts/Login.vue'
import SignUp from '@/views/accounts/SignUp.vue'
import Room from '@/views/Room.vue'
import Rank from '@/views/Rank.vue'
import CreateRoom from '@/views/CreateRoom.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/room/1',
    name: 'Room',
    component: Room
  },
  {
    path: '/rank',
    name: 'Rank',
    component: Rank
  },
  {
    path: '/room',
    name: 'CreateRoom',
    component: CreateRoom
  }


  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
