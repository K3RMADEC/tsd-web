import Vue from 'vue'
import Vuex from 'vuex'

import { auth } from './auth.module'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    baseUrl: 'http://localhost:8080/',
    barColor: 'rgba(0, 0, 0, .8), rgba(0, 0, 0, .8)',
    loginImage: '@/assets/login.jpg',
    drawer: null,
  },
  mutations: {
    SET_DRAWER (state, payload) {
      state.drawer = payload
    },
  },
  actions: {

  },
  modules: {
    auth,
  },
})
