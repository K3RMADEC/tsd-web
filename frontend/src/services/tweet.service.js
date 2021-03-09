import axios from 'axios'
import authHeader from './auth-header'

axios.defaults.baseURL = 'http://localhost:8080/api/'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
axios.defaults.headers['Access-Control-Allow-Origin'] = '*'
// todo  añadir la cabecera de authHeader aqui

class TweetService {
  getTweetsByDate (from, to) {
    return axios.get('getByDate', { headers: authHeader() }, { params: from, to })
  }
}

export default new TweetService()
