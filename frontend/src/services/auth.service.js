import axios from 'axios'

// axios.defaults.baseURL = 'http://localhost:8080/'
// axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// axios.defaults.headers['Access-Control-Allow-Origin'] = '*'

class AuthService {
  login (user, baseUrl) {
    return axios
      .post(baseUrl + 'login', {
        username: user.username,
        password: user.password,
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('token', response.data.accessToken)
        }
        if (response.data.role) {
            localStorage.setItem('role', response.data.role)
          }

        return response.data
      })
  }

  logout () {
    localStorage.removeItem('token')
    localStorage.removeItem('role')
  }
}

export default new AuthService()
