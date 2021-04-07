import axios from 'axios'
import store from '../store/store'
import authHeader from './auth-header'

const baseUrl = store.state.connectorBaseUrl + 'stream/'

class ConnectorService {
  getRules () {
    return axios.get(baseUrl + 'getRules', { headers: authHeader() })
  }

  createRule (rule) {
    return axios.post(baseUrl + 'createRule', rule, { headers: authHeader() })
  }

  deleteRule (ruleId) {
    return axios.delete(baseUrl + 'deleteRule', { params: { ruleId } }, { headers: authHeader() })
  }

  getStreamStatus () {
    return axios.get(baseUrl + 'status', { headers: authHeader() })
  }

  startStream () {
    return axios.get(baseUrl + 'start', { headers: authHeader() })
  }

  stopStream () {
    return axios.get(baseUrl + 'stop', { headers: authHeader() })
  }
}

export default new ConnectorService()
