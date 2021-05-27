import axios from 'axios'
import authHeader from './auth-header'

axios.defaults.baseURL = 'http://localhost:8080/api/tweets/'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
axios.defaults.headers['Access-Control-Allow-Origin'] = '*'
// todo  añadir la cabecera de authHeader aqui

class TweetService {
  getTweetsByDate (from, to, rules, page, itemsPerPage) {
    var request = { from, to, rules, page, itemsPerPage }
    return axios.post('getByDate', request, { headers: authHeader() })
  }

  countByDate (from, to, rules) {
    var request = { from, to, rules }
    return axios.post('countByDate', request, { headers: authHeader() })
  }

  getGeoTweetsByDate (from, to, rules) {
    var request = { from, to, rules }
    return axios.post('getGeoByDate', request, { headers: authHeader() })
  }
}

export default new TweetService()
