// Import System requirements
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueMoment from 'vue-moment'

import Buefy from 'buefy'

import { store } from './store/store';

// Import Views - Top level
import AppView from './components/App.vue'
import Login from './components/Login.vue'

Vue.use(Buefy)

Vue.use(VueMoment)
Vue.use(VueRouter)

const routes = [
  {path: '/', component: Login}
];

// Routing logic
var router = new VueRouter({  
  routes: routes,
  mode: 'history'
  // linkExactActiveClass: 'active',
  // scrollBehavior: function(to, from, savedPosition) {
  //   return savedPosition || { x: 0, y: 0 }
  // }
})

new Vue({
  el: '#root',
  store,
  router: router,
  render: h => h(AppView)
})
