import AuthService from '../services/auth.service'

const token = localStorage.getItem('token')
const role = localStorage.getItem('role')
const initialState = token
  ? { status: { loggedIn: true }, token, role }
  : { status: { loggedIn: false }, token: null, role: null }

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login ({ commit, rootState }, user) {
      return AuthService.login(user, rootState.baseUrl).then(
        user => {
          commit('loginSuccess', user)
          return Promise.resolve(user)
        },
        error => {
          commit('loginFailure')
          return Promise.reject(error)
        },
      )
    },
    logout ({ commit }) {
      AuthService.logout()
      commit('logout')
    },
  },
  mutations: {
    loginSuccess (state, user) {
      state.status.loggedIn = true
      state.token = user.accessToken
      state.role = user.role
    },
    loginFailure (state) {
      state.status.loggedIn = false
      state.token = null
      state.role = null
    },
    logout (state) {
      state.status.loggedIn = false
      state.token = null
      state.role = null
    },
  },
}
