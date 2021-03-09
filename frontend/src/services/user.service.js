import axios from 'axios'
import store from '../store/store'
import authHeader from './auth-header'

const baseUrl = store.state.baseUrl + 'api/user/'
// axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// axios.defaults.headers['Access-Control-Allow-Origin'] = '*'
// todo  añadir la cabecera de authHeader aqui

class UserService {
  getAll () {
    return axios.get(baseUrl + 'getAll', { headers: authHeader() })
  }

  register (user) {
    return axios.post(baseUrl + 'register', {
      username: user.username,
      password: user.password,
      role: user.role,
      },
      { headers: authHeader() })
  }

  remove (username) {
    return axios.delete(baseUrl + 'remove', { params: { username } }, { headers: authHeader() })
  }
}

export default new UserService()
